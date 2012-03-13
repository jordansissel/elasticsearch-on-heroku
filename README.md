# ElasticSearch on Heroku

This repo tracks any hacks and stuff necessary to make elasticsearch deployable on heroku.

## Running it locally:

  foreman start

## Unsolved problems

### Persistent Storage

Problem: Heroku currently doesn't offer any persistent storage, to my knowledge, so when
a dyno goes away, you'll lose data.

I don't know of any solutions to this.

### Authentication

Problem: ElasticSearch's REST api is unauthenticated, which is a problem on heroku
because everything is publicly accessible.

Might be able to hack something in if I can get access to the Jetty servlet
stuff used internal to elasticsearch.  Long term, adding support into
ElasticSearch for some kind of pluggable auth might make sense, and it could be
useful for both HTTP and Transport protocol.

### HTTP + Transport protocols

Problem: Heroku only provides one port per process, and elasticsearch serves
two protocols, one on each of two ports, it is impossible right now to serve
both HTTP and Node Transport protocols through heroku. 

If heroku supported 'HTTP Upgrade' it's possible ElasticSearch could talk
always to HTTP first, then upgrade to Node Transport if that is the desired
protocol.

### Clustering and Discovery

Problem: No support in elasticsearch to, on heroku, discovery neighboring
elasticsearch nodes.

As a process running in an app, how do I find the rest of my fleet? If I do
'heroku ps scale=10' how does process 10 know about processes 1-9?

If the app has credentials to use the Heroku client api, then we can discover
process listings that way, but this leaks very important credentials to your
elasticsearch deployments, so it may not be advisable.

Maybe a more locked-down application could be written to service read-only
queries for "what are my neighbors?" and other application deployment queries
a running app may want to ask. It still requires leaking credentials into a
running app, but perhaps one focused on specific security could make this
safer.

Alternately, some 'read only' credentials in heroku could achieve the same thing,
if such a feature exists or is on the roadmap.

## Hacks required

### Running in "local" mode

Currently, only one hack is required, disabling the 'transport' module in
elasticsearch. This is required because Heroku only permits you to have one
port, when elasticsearch will use two by default (http and node transport)

The hack is to use org.elasticsearch.node.NodeBuilder to create a "local"-only
elasticsearch node, which disables the transport module but leaves the HTTP
module activated.
