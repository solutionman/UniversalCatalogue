
sudo service postgresql start

cd backend
mvn clean install
java -jar target/catalog.jar

cd frontend 
npm install
npm run serve

http://localhost:8081/frontend/home

http://localhost:8080/backend/
http://localhost:8080/backend/catalogs


