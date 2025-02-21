## Compte docker hub
username : Hackathon2025
password : M=AH*+fEK5gDc,q

push images

```shell
docker tag local-image:tagname hackathon2025/skillstasks_repo:tagname
docker push hackathon2025/skillstasks_repo:tagname
```

## Pour ajouter le ansible.cfg 
export ANSIBLE_CONFIG=ansible.cfg

## Pour se connecter au site
voir dans l'ingress du deployment_front

## Pour se connecter à l'observabilité 
aller sur la vm control puis ``kubectl port-forward -n monitoring svc/prometheus-grafana --address 0.0.0.0 3000:80``
http://148.60.11.60:3000
identifiant: admin
mdp: prom-operator

sudo apt-get --purge remove postgresql postgresql-*


sudo ls -l /etc/postgresql/17/main/

-rw-r----- 1 postgres postgres 5924 févr. 18 11:52 /etc/postgresql/17/main/pg_hba.conf

mdp postgres: azerty123


cluster token glagent-EDJCZYzYjPQRPMbAjbQCg7VuwcXP4Z55JbeZxrWUztEV3UaXSQ


----------------------------------------------------------------


kube certs
sudo microk8s refresh-certs --cert ca.crt
sudo microk8s refresh-certs --cert front-proxy-client.crt
sudo microk8s refresh-certs --cert server.crt

helm upgrade --install agent-prod gitlab/gitlab-agent --namespace gitlab-agent-agent-prod --create-namespace --set image.tag=v17.8.2 --set config.token=glagent-EDJCZYzYjPQRPMbAjbQCg7VuwcXP4Z55JbeZxrWUztEV3UaXSQ --set config.kasAddress=wss://gitlab2.istic.univ-rennes1.fr/-/kubernetes-agent/ --kubeconfig .kube/config --insecure-skip-tls-verify

helm upgrade --install agent-prod gitlab/gitlab-agent \
  --namespace gitlab-agent-agent-prod \
  --create-namespace \
  --set image.tag=v17.8.2 \
  --set config.token=glagent-EDJCZYzYjPQRPMbAjbQCg7VuwcXP4Z55JbeZxrWUztEV3UaXSQ \
  --set config.kasAddress=wss://gitlab2.istic.univ-rennes1.fr/-/kubernetes-agent/ \
  --kubeconfig /chemin/vers/votre/kubeconfig  # Spécifiez le kubeconfig si nécessaire


# Étape de construction avec Maven et JDK 21
FROM maven:3.9.6-jdk-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Étape d'exécution avec JDK 21
FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]