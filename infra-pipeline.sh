az provider register --namespace Microsoft.Sql
az provider register --namespace Microsoft.Web
 
export NAME="mottumap"
export RG="rg-${NAME}"
export LOCATION="brazilsouth"
 
# SQL Server e Banco
export SERVER_NAME="sqlserver-${NAME}"
export USERNAME="admsql"
export PASSWORD="Fiap@2tdsvms"
export DBNAME="db-${NAME}"
 
#Plano e Web App
export WEBAPP_NAME="webapp-${NAME}"
export PLAN_NAME="plan-${NAME}"
export RUNTIME="JAVA:17-java17"
 
# Criando Server e Banco
az group create --name "$RG" --location "$LOCATION" &&
az sql server create -l "$LOCATION" -g "$RG" -n "$SERVER_NAME" -u "$USERNAME" -p "$PASSWORD" --enable-public-network true &&
az sql db create -g "$RG" -s "$SERVER_NAME" -n "$DBNAME" --service-objective Basic --backup-storage-redundancy Local --zone-redundant false &&
az sql server firewall-rule create -g "$RG" -s "$SERVER_NAME" -n AllowAll --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255
 
# Criando Plano e Web App
az appservice plan create -n "$PLAN_NAME" -g "$RG" --location "$LOCATION" --sku F1 --is-linux  &&
az webapp create -n "$WEBAPP_NAME" -g "$RG" -p "$PLAN_NAME" --runtime "$RUNTIME"
 
