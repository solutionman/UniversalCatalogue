package com.universal.catalog.controller;

import com.universal.catalog.entity.Catalog;
import com.universal.catalog.repository.CatalogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CatalogController {
    private final CatalogRepository catalogRepository;

    public CatalogController( CatalogRepository catalogRepository ) {
        this.catalogRepository = catalogRepository;
    }

    @PostMapping(value = "catalogs")
    public Map<String, Object> getOrderInfo(@RequestBody Map<String, Object> data) {

        String sortDesc = data.get("sortDesc").toString();
        int itemsPerPage = Integer.parseInt(data.get("itemsPerPage").toString());
        String orderBy = data.get("sortBy").toString();
        String forPageable;
        switch (orderBy){
            case "[orderNumber]" : forPageable = "ordernumber";
                break;
            case "[placementDate]" : forPageable = "placementdate";
                break;
            case "[orderType]" : forPageable = "ordertype";
                break;
            case "[orderPrice]" : forPageable = "orderprice";
                break;
            default: forPageable = "id";
        }
        int currPage = Integer.parseInt(data.get("page").toString()) - 1;
        Pageable pageable = PageRequest.of(currPage, itemsPerPage, Sort.by(sortDesc.equals("[true]") ? Sort.Direction.DESC : Sort.Direction.ASC, forPageable));
        Page<Catalog> page;
        String search = data.get("search").toString();
        if (search.isEmpty()){
            page = catalogRepository.findAll(pageable);
        } else {
            page = catalogRepository.findAll(pageable);
//            page = catalogRepository.findByOrdernumberContainingIgnoreCaseOrOrdertypeContainingIgnoreCaseOrDebtornameContainingIgnoreCase(
//                    search, search, search, pageable);
        }

        List<Catalog> orderInfoListList = page.getContent();

        List<Map<String, Object>> cardsList = new ArrayList<>();
        for( Catalog orderInfo : orderInfoListList ){
            Map<String, Object> currentCard = new HashMap<>();

            currentCard.put("id", orderInfo.getId());
            currentCard.put("orderNumber", "orderInfo.getOrdernumber()");
//            currentCard.put("placementDate", "orderInfo.getPlacementdate()");
            currentCard.put("orderType", "orderInfo.getOrdertype()");
            currentCard.put("orderPrice", "orderInfo.getOrderprice()");
//            currentCard.put("offersReceptionDate", "orderInfo.getOffersreceptiondate()");
            currentCard.put("orderStatus", "orderInfo.getOrderstatus()");
            currentCard.put("lawsuitNumber", "orderInfo.getLawsuitnumber()");
            currentCard.put("debtorName", " debtorname values ");
            currentCard.put("orderVision", "orderInfo.getOrdervision()");
            cardsList.add(currentCard);
        }
        long totalCards = page.getTotalElements();
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("cards", cardsList);
        resultMap.put("totalCards", totalCards);

        return resultMap;
    }

    @PostMapping(value = "newcatalog")
    Map<String, Object> newCatalog(@RequestBody Map<String, Object> fromForm){

        return new HashMap<>();
    }
    
}
