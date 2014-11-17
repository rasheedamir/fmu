'use strict';
angular.module('fmuClientApp')
    .directive('fmuUtredningTable', ['ngTableParams', '$filter', 'UtredningService', 'ngDialog', 'UTREDNING',
        function (ngTableParams, $filter, UtredningService, ngDialog, UTREDNING) {
            return {
                restrict: 'E',
                scope: {
                    tableParams: '=?tableParameters',
                    headerGroups: '=?',
                    headerFields: '=',
                    dateKey: '@',
                    footerHints: '=?',
                    startDate: '=?',
                    endDate: '=?',
                    eavropid: '=',
                    getDataCallback: '&'
                },
                controller: function ($scope) {
                    $scope.tableConstants = UTREDNING;
                    $scope.isEditColumn = function (key) {
                        return key == 'edit';
                    };

                    $scope.openEditRow = function (row) {
                        row.isEditExpanded = !row.isEditExpanded;
                    };

                    $scope.sort = function (key) {
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
                                        var promise = UtredningService.getAllEvents($scope.eavropid);

                                        promise.then(function (serverResponse) {
                                            params.total(serverResponse.length);
                                            $defer.resolve(serverResponse);
                                        })
                                    },
                                    $scope: $scope
                                });
                            /* jshint +W055 */
                        }
                    };
                },
                link: function (scope) {
                    scope.getValue = function (key, row) {
                        return scope.getDataCallback() ? scope.getDataCallback()(key, row) : row[key];
                    };
                    scope.rowClicked = function (row) {

                    };

                    scope.initTableParameters();
                },
                templateUrl: 'views/templates/fmu-utredning-table.html'
            };
        }]);
