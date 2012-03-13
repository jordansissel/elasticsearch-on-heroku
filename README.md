# ElasticSearch on Heroku

This repo tracks any hacks and stuff necessary to make elasticsearch deployable on heroku.

## Running it locally:

  foreman start

## Unsolved problems

* Persistent storage - Heroku currently doesn't offer any persistent storage,
  to my knowledge, so when a dyno goes away (every day?), you'll lose data.
* Authentication - elasticsearch's REST api is unauthenticated, which is a problem
  on heroku because everything is publicly accessible. Might be able to hack
  something in if I can get access to the Jetty servlet stuff used internal to
  elasticsearch. Long term, adding support into ElasticSearch for some
  kind of pluggable auth might make sense, and it could be useful for both
  HTTP and Transport protocol.s
* Clustering - Because heroku only provides one port per process, and
  elasticsearch serves two protocols, one on each of two ports, it is
  impossible right now to serve both HTTP and Node Transport protocols through
  heroku. If heroku supported 'HTTP Upgrade' it's possible ElasticSearch could
  talk always to HTTP first, then upgrade to Node Transport if that is
  the desired protocol.
* Clustering - Discovery is not a solved problem on Heroku, as far as I know. As
  a process running in an app, how do I find the rest of my fleet? If I do
  'heroku ps scale=10' how does process 10 know about processes 1-9?

## Hacks required

### Running in "local" mode

Currently, only one hack is required, disabling the 'transport' module in
elasticsearch. This is required because Heroku only permits you to have one
port, when elasticsearch will use two by default (http and node transport)

The hack is to use org.elasticsearch.node.NodeBuilder to create a "local"-only
elasticsearch node, which disables the transport module but leaves the HTTP
module activated.
