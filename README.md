ESHOP (8. 6. 2013)
-----------------

How to deploy the application:

- import (existing maven) project to JBDS
- you can either run application using studio tools or export WAR archive and place it to "standalone/deployments" dir in jboss-as-7.1.1 root and then run /bin/standalone.[bat|sh], deployment should be automatic
- application is mapped to "/explosivesShop/index.jsf" url

Actual version you can download from:
https://github.com/Pestret/pv243-project

APP is deployed to OpenShift: http://explosives-shop243.rhcloud.com/


For entering to authenticated part of web please use these credentials:

- Role CUSTOMER:  
    - you can register yourself as customer
    - or use username "user@user.cz" password "user"

- Role ADMIN:
    - use username "admin@admin.cz" password "admin" 