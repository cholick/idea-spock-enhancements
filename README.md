#### Maintenance Mode Notice
This project is only minimally maintained. I will still review merge PRs and fixes, and create new releases, but I likely won't fix things if it breaks as I haven't used the JVM in any real capacity in 10 years.

The code is using an API schedule for removal, as well as several other deprecated APIs; it is likely to cease to work.

#### Spock Enhancements
A [plugin for IntelliJ IDEA](http://plugins.jetbrains.com/plugin/7114) that improves integration with the Spock specification framework (http://code.google.com/p/spock/).

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
