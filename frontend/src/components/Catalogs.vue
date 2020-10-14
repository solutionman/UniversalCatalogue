<template>
  <v-container>
    <v-layout row>
      <v-flex
          xs12
          class="text-center"
          mt-5
      >
        <!--        <h1>Карточки</h1>-->
        <v-card-title>
          Заказы
          <v-spacer></v-spacer>
          <v-text-field
              v-model="search"
              append-icon="mdi-magnify"
              label="Поиск"
              single-line
              hide-details
              clearable
          ></v-text-field>
        </v-card-title>
        <v-data-table
            :headers="headers"
            :items="cards"
            :options.sync="options"
            :sort-by.sync="sortBy"
            :server-items-length="totalCards"
            :loading="loading"
            class="elevation-1"
            :items-per-page="10"
            :footer-props="footerProps"
            @click:row="handleClick"

        >
          <template v-slot:item.placementDate="{ item }">
            <!--                <span>{{ new Date(item.birthday).toLocaleString() }}</span>-->
            <span>{{
                item.placementDate == null ? 'Нет даты' : $moment( item.placementDate ).locale('ru').format('DD MMMM YYYY hh:mm')
              }}</span>
          </template>
          <template v-slot:item.offersReceptionDate="{ item }">
            <!--                <span>{{ new Date(item.birthday).toLocaleString() }}</span>-->
            <span>{{
                item.offersReceptionDate == null ? 'Нет даты' : $moment( item.offersReceptionDate ).locale('ru').format('DD MMMM YYYY')
              }}</span>
          </template>
        </v-data-table>

      </v-flex>

      <div id="app">
        {{ info }}
      </div>

      <v-flex
          xs8
          offset-xs2
          class="text-left"
          mt-5
      >
      </v-flex>

    </v-layout>
  </v-container>
</template>

<script>
import router from "../router";

export default {
  data () {
    return {
      search: '',
      totalCards: 0,
      cards: [],
      loading: true,
      options: {},
      sortBy: 'id',
      headers: [
        {
          text: 'ID',
          align: 'start',
          sortable: false,
          value: 'id',
        },
        { text: 'Номер Заказа', value: 'orderNumber' },
        { text: 'Дата Размещения', value: 'placementDate' },
        { text: 'Тип услуги', value: 'orderType' },
        { text: 'Цена', value: 'orderPrice' },
        { text: 'Прием предложений до', value: 'offersReceptionDate' },
        { text: 'Статус', value: 'orderStatus' },
        { text: 'Номер дела', value: 'lawsuitNumber' },
        { text: 'Должник', value: 'debtorName' },
        { text: 'Вид заказа', value: 'orderVision' },
      ],
      info: null,
      footerProps: {'items-per-page-options': [5, 10,15, 30, 50, 100]},
    }
  },
  watch: {
    options: {
      handler () {
        this.loading = true;
        let options = {};
        options["sortBy"] = this.options.sortBy;
        options["sortDesc"] = this.options.sortDesc;
        options["page"] = this.options.page;
        options["itemsPerPage"] = this.options.itemsPerPage;
        options["search"] = this.search == null ? '' : this.search;
        this.$axios({
          method: 'post',
          url: 'http://localhost:8080/backend/catalogs',
          headers: {},
          data: options
        }).then(response =>{
          console.log(response.data.cards)
          this.cards = response.data.cards;
          this.totalCards = response.data.totalCards;
          this.loading = false;
        }).catch((error) => {
          console.log(error);
        })
      },
      deep: true,
    },
    search: {
      handler () {
        this.loading = true;
        let options = {};
        options["sortBy"] = this.options.sortBy;
        options["sortDesc"] = this.options.sortDesc;
        options["page"] = this.options.page;
        options["itemsPerPage"] = this.options.itemsPerPage;
        options["search"] = this.search == null ? '' : this.search;
        this.$axios({
          method: 'post',
          url: 'http://localhost:8080/backend/catalogs',
          headers: {},
          data: options
        }).then(response =>{
          this.cards = response.data.cards;
          this.totalCards = response.data.totalCards;
          this.loading = false;
        }).catch((error) => {
          console.log(error);
        })
      },
      deep: true,
    },
  },
  mounted () {
    this.loading = true;
    let options = {};
    options["sortBy"] = this.options.sortBy;
    options["sortDesc"] = this.options.sortDesc;
    options["page"] = this.options.page;
    options["itemsPerPage"] = this.options.itemsPerPage;
    options["search"] = this.search;
    this.$axios({
      method: 'post',
      url: 'http://localhost:8080/backend/catalogs',
      headers: {},
      data: options
      // data: {
      //   sortBy: 'id', // This is the body part
      // }
    }).then(response =>{
      this.cards = response.data.cards;
      this.totalCards = response.data.totalCards;
      this.loading = false;
    }).catch((error) => {
      console.log(error);
    })

    // this.$axios.get('http://localhost:8080/backend/cards').then(response => {
    //   this.cards = response.data.cards;
    //   this.totalCards = response.data.totalCards;
    // }).catch((error) => {
    //   console.log(error);
    // })
    // this.getDataFromApi()
    //         .then(data => {
    //           this.desserts = data.items
    //           this.totalDesserts = data.total
    //         })
  },
  methods: {
    handleClick (value){
      // console.log( value );
      router.push({ name: 'Card', params: {
          id : value.id
        }  });
    },
    getDataFromApi () {
      // this.loading = true
      return new Promise((resolve, reject) => {
        const { sortBy, sortDesc, page, itemsPerPage } = this.options
        const itemsServer = fetch('/backend/cards')
            .then(response => response.json())
            .then(value => console.log(value) );
        console.log(itemsServer.then(resolve => resolve));
        let items = this.getDesserts()
        // let items = itemsServer;
        const total = items.length
        if (sortBy.length === 1 && sortDesc.length === 1) {
          items = items.sort((a, b) => {
            const sortA = a[sortBy[0]]
            const sortB = b[sortBy[0]]
            if (sortDesc[0]) {
              if (sortA < sortB) return 1
              if (sortA > sortB) return -1
              return 0
            } else {
              if (sortA < sortB) return -1
              if (sortA > sortB) return 1
              return 0
            }
          })
        }
        if (itemsPerPage > 0) {
          items = items.slice((page - 1) * itemsPerPage, page * itemsPerPage)
        }
        setTimeout(() => {
          this.loading = false
          resolve({
            items,
            total,
          })
        }, 1000)
      })
    },
    async axiosTest() {
      await this.$axios.get('/backend/cards').then(response => {
        // returning the data here allows the caller to get it through another .then(...)
        // console.log(response.data)
        return response.data
      })
    },
    getDesserts() {
      this.$axios
          .get('/backend/cards')
          .then((response) => {
            //console.log('Get response: ', response.data);
            // this.parseResponse(response);
            // respdata = response.data;
            // console.log( respdata );
            // items = response.data;
            // return respdata
            this.parseResponse(response);
          })
          .catch((error) => {
            // console.log(error);
            this.alert = true;
            this.parseResponse(error);
          })
      // function axiosTest() {
      //   this.$axios.get('http://localhost:8080/backend/cards').then(response => {
      //     // returning the data here allows the caller to get it through another .then(...)
      //     return response.data
      //   })
      // }
      this.axiosTest()
          .then(response => {
            // response.json({message: 'Request received!', data})
            // console.log(response);
          })
          .catch(err => console.log(err))
      // return respdata;
      // console.log(respdata);
    },
    parseResponse(response) {
      // return this.$axios.get('http://localhost:8080/backend/cards');
      // .then((response) => {
      //   console.log('Get response: ', response.data);
      //   // this.parseResponse(response);
      //   // respdata = response.data;
      //   // console.log( respdata );
      //   // items = response.data;
      //   // return respdata
      //   this.parseResponse(response);
      // })
      // .catch((error) => {
      //   // console.log(error);
      //   this.alert = true;
      //   this.parseResponse(error);
      // })
      // console.log(response);
      // return [
      //   {
      //     name: 'Frozen Yogurt',
      //     calories: 159,
      //     fat: 6.0,
      //     carbs: 24,
      //     protein: 4.0,
      //     iron: '1%'
      //   }
      // ];
      // const respObj = {};
      // respObj.name = response.name;
      // respObj.calories = response.calories;
      // respObj.fat = response.fat;
      // respObj.carbs = response.carbs;
      // respObj.protein = response.protein;
      // respObj.iron = response.iron;
      // return respObj;
      // return [JSON.parse(response)]
      // return JSON.stringify(response)
    },
  },
}
</script>
