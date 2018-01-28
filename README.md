# dawn
main repo

[ basic architecure ]

The architeture is parted in modules and layers, to use generalization, inheritance etc. without circles.

-Layers-

ui*       user interaction; surfaces ...
model     abstraction from ui to logic, contains wrapper of persistable beans
logic     logic of all shades, interfaces (if any)
data      persistable beans and their persistand logic

*named as "view" or "standalone"

-Dependencies (Layers)-

data  <- logic -> model <- ui

-Modules-

primary   <-  secondary   <-tertiary <- ...
