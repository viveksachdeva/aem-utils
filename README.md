# Machine Translation in AEM
This repository basic structure for Custom Machine Translation Connector in AEM. It include dependencies that are needed for machine translation to work.

This also includes example implementation for Sling Job Processing that is way for persistent event handling. It is an alternate and will ultimately be a substitute of OSGi Event Handling as in OSGi Eventing Mechanism there is no guarantee for delivery but Sling Jobs make sure that delivery is guaranteed and any event is handled at most once.


This a content package project generated using the multimodule-content-package-archetype.

Building
--------

This project uses Maven for building. Common commands:

From the root directory, run ``mvn -PautoInstallPackage clean install`` to build the bundle and content package and install to a CQ instance.

From the bundle directory, run ``mvn -PautoInstallBundle clean install`` to build *just* the bundle and install to a CQ instance.


Specifying CRX Host/Port
------------------------

The CRX host and port can be specified on the command line with:
mvn -Dcrx.host=otherhost -Dcrx.port=5502 <goals>



