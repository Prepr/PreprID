'use strict'

admin = angular.module('admin', [])

admin.config [
  '$routeProvider',
  ($routeProvider) ->
    $routeProvider.when '/common/admin/navigation',
      templateUrl: 'assets/common/admin/partials/navigation-edit.tpl.html'
    $routeProvider.otherwise
      redirectTo: '/'
]