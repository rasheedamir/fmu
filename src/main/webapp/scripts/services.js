'use strict';

/* Services */
angular.module('fmuClientApp').factory('EavropService', ['$q', '$http', 'RESTURL',
    function($q, $http, RESTURL){
        return {
            getEavrops: function(){
                return $http.get(RESTURL.eavrop).then(function(data){
                    // Success
                    return data.data;
                }, function(err){
                    // Failed to retrieve data
                    return $q.reject(err.data);
                });
            }
        };
    }])

    .factory('DateSelectionChangeService',
    function($rootScope){
        var service = {};
        service.startDate = null;
        service.endDate = null;

        service.setInitialDateRange = function(startDate, endDate){
            this.startDate = startDate;
            this.endDate = endDate;
            $rootScope.$broadcast('initialDateIsSet');
        };

        service.update = function(date1, date2){
            this.startDate = date1;
            this.endDate = date2;
            $rootScope.$broadcast('newDateSelected');
        };
        return service;
    });
