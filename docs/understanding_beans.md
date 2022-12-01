# JavaBeans



On JDK-provided beans (e.g. `JButton`), a PropertyChangeEvent is fired every time 
__any__ _default property_ is changed. On the other hand, events on _custom properties_
changes must be fired manually with `firePropertyChange("value", oldValue, newValue)`

_firePropertyChange_ method belongs to `PropertyChangeSupport`
[API](https://docs.oracle.com/en/java/javase/17/docs/api/java.desktop/java/beans/PropertyChangeSupport.html).
It is __thread-safe__, a thread locks the object in order to manipulate listeners,
fire-while-adding is prevented.

## Maybe irrilevant
>A __*bean builder tool* uses introspection to examine the bean class__.
>Based on this inspection, the bean builder tool can figure out the bean's properties,
>methods, and events. [1]


## Definitions
* By _default property_ I mean any property already defined in provided JavaBeans (e.g. `JButton.text`)
* By _custom property_ I mean properties added on classes which inherit from provided JavaBeans
* "A _builder tool_ like NetBeans" [1]<br>

## Bibliography
[1] [Writing JavaBeans](https://docs.oracle.com/javase/tutorial/javabeans/writing/index.html)<br>
[2] [An amendment to the JavaBeans Specification](https://www.oracle.com/java/technologies/javase/javabeans-getlisteners.html)<br>
[3] docs/beans.101.pdf (JDK 1.1 JavaBeans spec)