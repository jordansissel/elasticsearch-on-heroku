# ElasticSearch on Heroku

This repo tracks any hacks and stuff necessary to make elasticsearch deployable on heroku.


## Running it locally:

  foreman start

## Hacks required

### Running in "local" mode

Currently, only one hack is required, disabling the 'transport' module in
elasticsearch. This is required because Heroku only permits you to have one
port, when elasticsearch will use two by default (http and node transport)

The hack is to use org.elasticsearch.node.NodeBuilder to create a "local"-only
elasticsearch node, which disables the transport module but leaves the HTTP
module activated.
