'use strict'

require([
    'angular',
    'angular-resource',
    'angular-route',
    '/web/vassets/javascripts/web-app.js'
    '/users/vassets/javascripts/users-app.js'
  ], (angular) ->

  paws = angular.module('paws', ['ngResource', 'ngRoute', 'web', 'users'])

  paws.factory 'navigation',
  ($resource) ->
    $resource('navigation')

  paws.config [
    '$routeProvider',
    ($routeProvider) ->
      $routeProvider.when '/',
        templateUrl: 'web/vassets/partials/welcome.tpl.html'
      $routeProvider.otherwise
        redirectTo: '/'
  ]

  angular.bootstrap(document, ['paws']);
)