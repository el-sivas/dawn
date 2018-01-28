See "Raw"

# dawn
main repo

[ basic architecure ]
The architeture is parted in modules and layers, to use generalization, inheritance etc. without circles.

-Layers-
ui*       user interaction; surfaces ...
model     abstraction from ui to logic, contains wrapper of persistable beans, only interfaces
logic     logic of all shades, interfaces (if any), implements "model"
data      persistable beans and their persistand logic

*named as "view" or "standalone"

-Dependencies (Layers)-
data  < logic > model < ui

-Dependencies (Modules)-
primary   <  secondary   < tertiary < ...

-Overview-
primary-ui    < secondary-ui    < tertiary-ui     < ...
  \/                \/              \/
primary-model < secondary-model < tertiary-model  < ...
  \/                \/              \/
primary-logic < secondary-logic < tertiary-logic  < ...
  \/                \/              \/
primary-data  < secondary-data  < tertiary-data   < ...

-Specials-
dawn-root:            manages all external dependencies
dawn-central-basic:   
