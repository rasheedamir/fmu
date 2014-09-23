'use strict';

angular.module('fmuClientApp').
factory('Eavrop', ['$resource',

function($resource){
   return $resource('/eavrop/:eavropId',{}, {
       update: {method: 'PUT', params: {eavropId: '@eavropId'}}
   });
}]);
