'use strict';
angular.module('fmu.widgets')
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
                    // Todo enable sortable fields
                    $scope.isSortable = function () {
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
                                    getData: function ($defer) {
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
                templateUrl: 'widgets/fmu-simple-table.html'
            };
        }]);
