'use strict';
angular.module('fmuClientApp')
    .directive('fmuTable', ['ngTableParams', '$filter', 'EavropService', 'EAVROP_TABLE',
        function (ngTableParams, $filter, EavropService, EAVROP_TABLE) {
            return {
                restrict: 'E',
                scope: {
                    tableParams: '=tableParameters',
                    headerGroups: '=?',
                    headerFields: '=?',
                    dateKey: '@',
                    footerHints: '=?',
                    startDate: '=?',
                    endDate: '=?',
                    eavropStatus: '=?'
                },
                controller: function ($scope) {
                    $scope.sort = function (key) {
                        var params = {};
                        params[key] = $scope.tableParams.isSortBy(key, 'asc') ? 'desc' : 'asc';
                        $scope.tableParams.sorting(params);
                        $scope.currentSortKey = key;
                    };

                    $scope.getValue = function (key, eavrop) {
                        switch (key) {
                            case $scope.dateKey:
                                return $filter('date')(eavrop[key], EAVROP_TABLE.dateFormat);
                            default:
                                return eavrop[key];
                        }
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
                                        var promise = EavropService.getEavrops(
                                            $scope.startDate ? $scope.startDate : null,
                                            $scope.endDate ? $scope.endDate : null,
                                            $scope.eavropStatus ? $scope.eavropStatus : null,
                                            params.page() - 1,
                                            params.count(),
                                            $scope.currentSortKey ? EAVROP_TABLE.sortKeyMap [$scope.currentSortKey]: 'arendeId',
                                            params.sorting()[$scope.currentSortKey] ? params.sorting()[$scope.currentSortKey].toUpperCase() : 'ASC'
                                        );

                                        promise.then(function (serverResponse) {
                                            params.total(serverResponse.totalElements);
                                            $defer.resolve(serverResponse.eavrops);
                                        })

                                    },
                                    $scope: $scope
                                });
                            /* jshint +W055 */
                        }
                    };
                },
                link: function (scope) {
                    scope.rowClicked = function (row) {

                    };

                    scope.initTableParameters();
                },
                templateUrl: 'views/templates/fmu-table.html'
            };
        }]);
