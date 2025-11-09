az provider register --namespace Microsoft.Sql
az provider register --namespace Microsoft.Web
 
NAME="mottumap" 
RG="rg-$NAME" 
LOCATION="brazilsouth"
 
# SQL Server e Banco
SERVER_NAME="sqlserver-$NAME"
USERNAME="admsql"
PASSWORD="Fiap@2tdsvms"
DBNAME="db-$NAME"
 
#Plano e Web App
WEBAPP_NAME="webapp-$NAME"
PLAN_NAME="plan-$NAME"
RUNTIME="JAVA:17-java17"
sku=F1
imageACR="$NOME_ACR.azurecr.io/$NOME_REPOSITORY:latest"
port=8080

#ACI
NOME_ACR="acr$NAME"
NOME_REPOSITORY="repository$NAME"
skuACR=Standard


echo "Criando RG"
az group create --name $RG --location $LOCATION

echo "Criando Server e Banco"
az sql server create -l $LOCATION -g $RG -n $SERVER_NAME -u $USERNAME -p $PASSWORD --enable-public-network true &&
az sql db create -g $RG -s $SERVER_NAME -n $DBNAME --service-objective Basic --backup-storage-redundancy Local --zone-redundant false &&
az sql server firewall-rule create -g $RG -s $SERVER_NAME -n AllowAll --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255
 
echo "Criando ACR"
az acr create --resource-group $RG --name $NOME_ACR --sku $skuACR
az acr update --name $NOME_ACR --resource-group $RG --admin-enabled true --public-network-enabled true --anonymous-pull-enabled true

echo "Criando Plano e Web App"
az appservice plan create -n $PLAN_NAME -g $RG --location $LOCATION --sku F1 --is-linux  &&
az webapp create -n $WEBAPP_NAME -g $RG -p $PLAN_NAME --deployment-container-image-name $imageACR # <- para uma imagem do ACR
az webapp config appsettings set --resource-group $RG --name $WEBAPP_NAME --settings WEBSITES_PORT=$port

# az webapp create -n $WEBAPP_NAME -g $RG -p $PLAN_NAME --runtime $RUNTIME  <- para java
