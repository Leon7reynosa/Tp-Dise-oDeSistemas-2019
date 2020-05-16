define(
  'CustomHelpers'
, [
    'Handlebars'
  ]
, function (
    Handlebars
  )
{
  'use strict'

  Handlebars.registerHelper('boogie', function(text)
  {
    return new Handlebars.SafeString(text);;
  });
});