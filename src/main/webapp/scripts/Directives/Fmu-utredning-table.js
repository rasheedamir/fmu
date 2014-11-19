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
                    footerHints: '=?',
                    eavropid: '=',
                    getDataCallback: '&',
                    rowModifiable: '=?'
                },
                controller: function ($scope) {
                    function createDataPackage(bookingId, newStatus, comment) {
                        return {
                            eavropId: $scope.eavropid,
                            bookingId: bookingId,
                            bookingStatus: newStatus,
                            comment: comment
                        }
                    };

                    $scope.cancelChange = function (rowData) {
                        $scope.toogleEditRow(rowData);
                    };
                    $scope.tableConstants = UTREDNING;
                    // TODO when eavrop status is onhold disable editing functionalities
                    $scope.isEditColumn = function (key, row) {
                        if (!row)
                            return key == 'edit';
                        return key == 'edit'
                            && (row.tolkStatus || row.handelseStatus);
                    };

                    $scope.toogleEditRow = function (row) {
                        row.isEditExpanded = !row.isEditExpanded;
                    };

                    $scope.changeTolkStatus = function (rowData) {
                        var dataPackage = createDataPackage(rowData.bookingId,
                            rowData.selectedTolkStatus.name,
                            rowData.selectedTolkStatus.requireComment ? rowData.tolkComment : null);
                        var promise = UtredningService.changeTolkBooking(dataPackage);
                        promise.then(function () {
                                // Success
                                $scope.toogleEditRow(rowData);
                                $scope.tableParams.reload();
                            },
                            function () {
                                // Failed
                            });
                    };

                    $scope.changeHandelseStatus = function (rowData) {
                        var dataPackage = createDataPackage(rowData.bookingId,
                            rowData.selectedHandelseStatus.name,
                            rowData.selectedHandelseStatus.requireComment ? rowData.handelseComment : null);
                        var promise = UtredningService.changeBooking(dataPackage);
                        promise.then(function () {
                                // Success
                                $scope.toogleEditRow(rowData);
                                $scope.tableParams.reload();
                            },
                            function () {
                                // Failed
                            });
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
                                            var orderedData = params.sorting() ?
                                                $filter('orderBy')(serverResponse, params.orderBy()) :
                                                serverResponse;

                                            params.total(orderedData.length);
                                            $defer.resolve(orderedData);
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

// TODO set default tolk/booking status and commenst in expanded tabs for indicating current stauses