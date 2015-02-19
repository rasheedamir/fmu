(function() {
    'use strict';
    angular.module('fmu.widgets')
        .directive('fmuUtredningTable', fmuUtredningTable);

    fmuUtredningTable.$inject = ['ngTableParams', '$filter', 'investigationService', '$modal'];

    function fmuUtredningTable(ngTableParams, $filter, investigationService, $modal) {
        return {
            restrict: 'E',
            scope: {
                tableParams: '=?tableParameters',
                headerGroups: '=?',
                headerFields: '=',
                footerHints: '=?',
                eavropid: '=',
                accessDataCallback: '&',
                rowModifiable: '=?'
            },
            controller: function($scope) {
                $scope.tableConstants = investigationService.getTextConstants();
                // TODO when eavrop status is onhold disable editing functionalities
                $scope.cancelChange = cancelChangeFn;
                $scope.isEditColumn = isEditColumnFn;
                $scope.toogleEditRow = toogleEditRowFn;
                $scope.changeTolkStatus = changeTolkStatusFn;
                $scope.changeHandelseStatus = changeHandelseStatusFn;
                $scope.sort = sortFn;
                $scope.initTableParameters = initTableParametersFn;

                function cancelChangeFn(rowData) {
                    $scope.toogleEditRow(rowData);
                }


                function isEditColumnFn(row) {
                    return (row.tolkStatus || row.handelseStatus);
                }

                function toogleEditRowFn(row) {
                    row.selectedTolkStatus = row.tolkStatus.currentStatus;
                    if (row.selectedTolkStatus && row.selectedTolkStatus.requireComment) {
                        row.tolkComment = row.tolkStatus.comment;
                    }

                    row.selectedHandelseStatus = row.handelseStatus.currentStatus;
                    if (row.selectedHandelseStatus && row.selectedHandelseStatus.requireComment) {
                        row.handelseComment = row.handelseStatus.comment;
                    }

                    row.isEditExpanded = !row.isEditExpanded;
                }

                function changeTolkStatusFn(rowData) {
                    var confirmModal = $modal.open({
                        templateUrl: 'eavrop-overview/investigation/changeBookingConfirmationModal.html',
                        resolve: {
                            parent: function() {
                                return $scope;
                            }
                        },
                        controller: function($scope, parent) {
                            $scope.save = function() {
                                var dataPackage = createDataPackage(rowData.bookingId,
                                    rowData.selectedTolkStatus.name,
                                    rowData.selectedTolkStatus.requireComment ? rowData.tolkComment : null);
                                var promise = investigationService.changeTolkBooking(dataPackage);
                                promise.then(function() {
                                        // Success
                                        confirmModal.close();
                                        parent.toogleEditRow(rowData);
                                        parent.tableParams.reload();
                                    },
                                    function(error) {
                                        $scope.bookingChangeErrors = [error];
                                    });
                            };

                            $scope.cancel = function() {
                                confirmModal.close();
                            };
                        }
                    });
                }

                function changeHandelseStatusFn(rowData) {
                    var confirmModal = $modal.open({
                        templateUrl: 'eavrop-overview/investigation/changeBookingConfirmationModal.html',
                        resolve: {
                            parent: function() {
                                return $scope;
                            }
                        },
                        controller: function($scope, parent) {
                            $scope.save = function() {
                                var dataPackage = createDataPackage(rowData.bookingId,
                                    rowData.selectedHandelseStatus.name,
                                    rowData.selectedHandelseStatus.requireComment ? rowData.handelseComment : null);
                                var promise = investigationService.changeBooking(dataPackage);
                                promise.then(function() {
                                        // Success
                                        confirmModal.close();
                                        parent.toogleEditRow(rowData);
                                        parent.tableParams.reload();
                                    },
                                    function(error) {
                                        // Failed
                                        $scope.bookingChangeErrors = [error];
                                    });
                            };

                            $scope.cancel = function() {
                                confirmModal.close();
                            };

                            $scope.getAdditionalExplaination = function() {
                                if (!rowData) {
                                    return null;
                                }

                                return investigationService.getTextConstants().eventsRequireConfirmation[rowData.selectedHandelseStatus.name];
                            };
                        }
                    });
                }

                function sortFn(key) {
                    var params = {};
                    params[key] = $scope.tableParams.isSortBy(key, 'asc') ? 'desc' : 'asc';
                    $scope.tableParams.sorting(params);
                    $scope.currentSortKey = key;
                }

                function initTableParametersFn() {
                    if (!$scope.tableParams) {

                        /* jshint -W055 */ // XXX: ngTableParams.
                        $scope.tableParams = new ngTableParams({
                            page: 1, // show first page
                            count: 10 // count per page
                        }, {
                            getData: function($defer, params) {
                                var promise = investigationService.getAllEvents($scope.eavropid);

                                promise.then(function(serverResponse) {
                                    var orderedData = params.sorting() ?
                                        $filter('orderBy')(serverResponse, params.orderBy()) :
                                        serverResponse;

                                    params.total(orderedData.length);
                                    $defer.resolve(orderedData);
                                });
                            },
                            $scope: $scope
                        });
                        /* jshint +W055 */
                    }
                }

                function createDataPackage(bookingId, newStatus, comment) {
                    return {
                        eavropId: $scope.eavropid,
                        bookingId: bookingId,
                        bookingStatus: newStatus,
                        comment: comment
                    };
                }
            },
            link: function(scope) {
                scope.getValue = function(key, row) {
                    return scope.accessDataCallback() ? scope.accessDataCallback()(key, row) : row[key];
                };

                scope.initTableParameters();
            },
            templateUrl: 'widgets/fmu-utredning-table.html'
        };
    }
})();