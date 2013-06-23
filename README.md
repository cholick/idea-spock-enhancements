#### Spock Enhancements

A plugin for IntelliJ IDEA that improves integration with the Spock specification framework (http://code.google.com/p/spock/).

#### Features
* Font changes for the Spock labels. The font change can be configured under IDE Settings -> Spock.
* Inspections to highlight block ordering errors such as an expect block following a when block. The inspections appear under Inspections -> Groovy -> Spock.
* A collection of live templates for feature methods contributed by @fpape

#### Spec without plugin

![before.png](https://raw.github.com/wiki/mcholick/idea-spock-enhancements/before.png)

#### Spec with plugin

![after.png](https://raw.github.com/wiki/mcholick/idea-spock-enhancements/after.png)

#### Building

Visit http://www.jetbrains.org/display/IJOS/Writing+Plug-ins for instructions on configuring your IDE to build plugins.

##### Project variables
* **idea_sdk** For library paths. Set to a current IntelliJ installation path (e.g. ```/Applications/IntelliJ IDEA 12 CE.app```)
* **idea_sdk_11** For intelij-adapter-11 library paths. Set to IntelliJ 11 installation. (e.g. ```/Applications/IntelliJ IDEA 11.1 CE.app```)
* **idea_sdk_12** For intelij-adapter-12 library paths. Set to IntelliJ 12 installation. (e.g. ```/Applications/IntelliJ IDEA 12 CE.app```)
* **idea_sdk_name** Used as project sdk. Set to a configured current sdk (e.g. ```IDEA IC-123.169```)
* **idea_sdk_name** intelij-adapter-11 module sdk. Set to a configured IntelliJ 11 sdk (e.g. ```IDEA IC-117.1054```)
* **idea_sdk_name** intelij-adapter-12 module sdk. Set to a configured IntelliJ 12 sdk (e.g. ```IDEA IC-123.169```)
