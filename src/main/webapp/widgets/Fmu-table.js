'use strict';
angular.module('fmu.widgets')
    .directive('fmuTable', ['ngTableParams', '$state', '$filter', 'Dataservice',
        function(ngTableParams, $state, $filter, Dataservice) {
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
                    eavropStatus: '=?',
                    accessDataCallback: '&'
                },
                controller: function($scope) {
                    var eavropConstants = Dataservice.getEavropConstants;
                    $scope.isSortable = function(/*key*/) {
                        return false; //eavropConstants.sortKeyMap.hasOwnProperty(key); //TODO reenable this
                    };
                    $scope.sort = function(key) {
                        if (!$scope.isSortable(key)) {
                            return;
                        }

                        var params = {};
                        params[key] = $scope.tableParams.isSortBy(key, 'asc') ? 'desc' : 'asc';
                        $scope.tableParams.sorting(params);
                        $scope.currentSortKey = key;
                    };

                    $scope.initTableParameters = function() {
                        if (!$scope.tableParams) {

                            /* jshint -W055 */ // XXX: ngTableParams.
                            $scope.tableParams = new ngTableParams({
                                page: 1, // show first page
                                count: 10 // count per page
                            }, {
                                getData: function($defer, params) {
                                    var data = Dataservice.getOverviewEavrops(
                                        $scope.startDate ? $scope.startDate : null,
                                        $scope.endDate ? $scope.endDate : null,
                                        $scope.eavropStatus ? $scope.eavropStatus : null,
                                        params.page() - 1,
                                        params.count(),
                                        $scope.currentSortKey ? eavropConstants.sortKeyMap[$scope.currentSortKey] : 'arendeId',
                                        params.sorting()[$scope.currentSortKey] ? params.sorting()[$scope.currentSortKey].toUpperCase() : 'ASC'
                                    );

                                        params.total(data.totalElements);
                                        $defer.resolve(data.eavrops);

                                },
                                $scope: $scope
                            });
                            /* jshint +W055 */
                        }
                    };
                },
                link: function(scope) {
                    scope.getValue = function(key, row) {
                        return scope.accessDataCallback() ? scope.accessDataCallback()(key, row) : row[key];
                    };

                    scope.rowClicked = function(row) {
                        $state.go('eavrop.order.contents', {
                            eavropId: row.eavropId
                        });
                    };

                    scope.initTableParameters();
                },
                templateUrl: 'widgets/fmu-table.html'
            };
        }
    ]);