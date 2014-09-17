'use strict';

/* Controllers */
angular.module('fmuClientApp')
    .controller('EavropController',
    ['$scope','$filter', 'EavropService', 'ngTableParams','EAVROPHEADERS','DateSelectionChangeService',
    function($scope, $filter, EavropService, ngTableParams, EAVROPHEADERS ,DateSelectionChangeService){
        $scope.tableHeaders = EAVROPHEADERS;
        $scope.tableKeys = [];
        var dateSortKey = 'creationTime';
        var applyDateFilter = function(eavrops){
            return $filter('dateFilter')(eavrops, dateSortKey, DateSelectionChangeService.startDate, DateSelectionChangeService.endDate);
        };

        // Fetch eavrops data from server
        //EavropService.getEavrops().then(function(result){
            $scope.eavrops = [{'arendeId':'123421','utredningType':'AFU','bestallareOrganisation':'In progress','enhet':'In progress','creationTime':1445451264483,'patientCity':'Linköping','mottagarenOrganisation':'In progress','utredare':'In progress','status':'In progress','antalDagarEfterForfragan':93,'color':'#ffffff'},
                {'arendeId':'753423','utredningType':'SLU','bestallareOrganisation':'In progress','enhet':'In progress','creationTime':1490811264484,'patientCity':'Göteborg','mottagarenOrganisation':'In progress','utredare':'In progress','status':'In progress','antalDagarEfterForfragan':89,'color':'#ffff99'},
                {'arendeId':'44240','utredningType':'AFU','bestallareOrganisation':'In progress','enhet':'In progress','creationTime':1481310864484,'patientCity':'Stockholm','mottagarenOrganisation':'In progress','utredare':'In progress','status':'In progress','antalDagarEfterForfragan':92,'color':'#ffcccc'},
                {'arendeId':'78743','utredningType':'TMU','bestallareOrganisation':'In progress','enhet':'In progress','creationTime':1492884864484,'patientCity':'oskarshamn','mottagarenOrganisation':'In progress','utredare':'In progress','status':'In progress','antalDagarEfterForfragan':95,'color':'#ffcccc'}];
            if($scope.eavrops !== null && $scope.eavrops.length > 0){
                // Set initial date range
                if($scope.eavrops.length > 0){
                    $scope.tableKeys = _.keys($scope.eavrops[0]);
                    var ordered = $filter('orderBy')($scope.eavrops, dateSortKey, false);
                    DateSelectionChangeService.setInitialDateRange(_.first(ordered)[dateSortKey], _.last(ordered)[dateSortKey]);
                }
            }

            /* jshint -W055 */ // XXX: ngTableParams.
            // Set up ngtable options
            var filteredData = applyDateFilter($scope.eavrops);
            if(!$scope.tableParams){
                /* jshint -W055 */ // XXX: ngTableParams.
                $scope.tableParams = new ngTableParams({
                    page: 1,            // show first page
                    count: 10          // count per page
                }, {
                    total: filteredData.length, // length of data
                    getData: function($defer, params) {
                        filteredData = params.sorting() ?
                            $filter('orderBy')(filteredData, params.orderBy()) :
                            filteredData;

                        $defer.resolve(filteredData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                    }
                });
                /* jshint +W055 */
                $scope.tableParams.settings().$scope = $scope;
            }

            $scope.getFormattedDate = function(date){
                return $filter('date')(date, 'dd-MM-yyyy');
            };

            $scope.sort = function(key, tableParams){
                var params = {};
                params[key] = tableParams.isSortBy(key, 'asc') ? 'desc' : 'asc';
                tableParams.sorting(params);
                $scope.$broadcast();
            };

            // Listen to date changes and apply filter
            $scope.$on('newDateSelected', function(){
                filteredData = applyDateFilter($scope.eavrops);
                $scope.tableParams.reload();
            });
        //});
    }])

    .controller('DateSelectionController', ['$scope', 'DateSelectionChangeService',
    function ($scope, DateSelectionChangeService) {

        $scope.$on('initialDateIsSet', function(){
            $scope.startDate = new Date(DateSelectionChangeService.startDate);
            $scope.endDate = new Date(DateSelectionChangeService.endDate);
        });

        $scope.doFilter = function(){
            DateSelectionChangeService.update($scope.startDate, $scope.endDate);
        };

        $scope.clearStartDate = function () {
            $scope.startDate = null;
        };

        $scope.clearEndDate = function () {
            $scope.endDate = null;
        };

        // Disable weekend selection
        $scope.disabled = function(date, mode) {
            return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
        };

        $scope.openStart = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.startDateOpened = true;
        };

        $scope.openEnd = function($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.endDateOpened = true;
        };
        $scope.dateFormat = 'dd-MM-yyyy';
    }]);