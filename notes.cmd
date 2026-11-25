C:\Users\nasee\t_work\kafka>set PATH=C:\spring-4.1.1\bin;%PATH%

spring init --build=maven --java-version=17 --group-id=com.nnsyed --artifact-id=orders --name=orders --package-name=com.nnsyed.orders --dependencies=web,kafka,data-jpa,security,oracle,lombok,actuator --extract orders

spring init --build=maven --java-version=17 --group-id=com.nnsyed --artifact-id=notification --name=notification --package-name=com.nnsyed.notification --dependencies=web,kafka,data-jpa,security,lombok,actuator --extract notification




