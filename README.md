# GeoFence
An open platform that connects users' phones with Pebble Watches in order to create virtual barriers in the
physical world.

### Use Cases
* Determine outside sport/recreational activity boundaries. e.g. CTF, Paintball, Hide and Go Seek, Airsoft
* Inform users on restricted areas in an easy to use and intuitive way
    * University Campuses
    * Military Bases
    * Company Campuses

### How it works
The android application records the current location, center and radius size of fence. It forwards the distance to the fence,
the difference between current location and center of fence, to the Pebble Watch display. If the distance to the fence is less
than 0, a message is sent to the Pebble Watch indicating that the fence has been exited. The Pebble Watch will then display
the message and vibrate the user to notify him of the change.
 



