'use strict';
angular.module('fmuClientApp')
    .directive('fmuSimpleTable', ['ngTableParams',
        function (ngTableParams) {
            return {
                restrict: 'E',
                scope: {
                    tableParams: '=tableParameters',
                    headerGroups: '=?',
                    headerFields: '=?',
                    accessDataCallback: '&',
                    showAllRows: '=?',
                    restDataCallback: '&'
                },
                compile: function(element, attrs){
                    if(!attrs.showAllRows){
                        attrs.showAllRows = false;
                    }
                },
                controller: function ($scope) {
                    $scope.isSortable = function (key) {
                        return false;
                    };
                    $scope.sort = function (key) {
                        if(!$scope.isSortable(key)){
                            return;
                        }

                        var params = {};
                        params[key] = $scope.tableParams.isSortBy(key, 'asc') ? 'desc' : 'asc';
                        $scope.tableParams.sorting(params);
                        $scope.currentSortKey = key;
                    };

                    $scope.initTableParameters = function () {
                        if (!$scope.tableParams) {

                            /* jshint -W055 */ // XXX: ngTableParams.
                            $scope.tableParams = new ngTableParams({
                                    page: 1, // show first page
                                    count: 10 // count per page
                                },
                                {
                                    getData: function ($defer, params) {
                                        var promise = $scope.getRestData();
                                        if(promise == null){
                                            return;
                                        }

                                        promise.then(function (serverResponse) {
                                            if($scope.showAllRows){
                                                params.total(serverResponse.totalElements);
                                            }

                                            $defer.resolve(serverResponse.eavrops);
                                        });

                                    },
                                    $scope: $scope
                                });
                            /* jshint +W055 */
                        }
                    };
                },
                link: function (scope) {
                    scope.getValue = function (key, row) {
                        return scope.accessDataCallback() ? scope.accessDataCallback()(key, row) : row[key];
                    };

                    scope.getRestData = function () {
                        return scope.restDataCallback() ? scope.restDataCallback()() : null;
                    };

                    scope.initTableParameters();
                },
                templateUrl: 'views/templates/fmu-simple-table.html'
            };
        }]);
