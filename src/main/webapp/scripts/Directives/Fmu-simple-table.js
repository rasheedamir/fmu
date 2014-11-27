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
                    tabularData: '='
                },
                controller: function ($scope) {
                    $scope.isSortable = function (key) {
                        return false;
                    };

                    $scope.sort = function (key) {
                        if (!$scope.isSortable(key)) {
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
                                        console.log($scope.tabularData);
                                        $defer.resolve($scope.tabularData);
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

                    scope.initTableParameters();
                },
                templateUrl: 'views/templates/fmu-simple-table.html'
            };
        }]);
