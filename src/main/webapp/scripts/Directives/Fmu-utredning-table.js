'use strict';
angular.module('fmuClientApp')
    .directive('fmuUtredningTable', ['ngTableParams', '$filter', 'UtredningService', 'ngDialog',
        function (ngTableParams, $filter, UtredningService, ngDialog) {
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
                    eavropid: '='
                },
                controller: function ($scope) {
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

                    $scope.getValue = function (key, eavrop) {
                        switch (key) {
                            case $scope.dateKey:
                                return $filter('date')(eavrop[key], EAVROP_TABLE.dateFormat);
                            case 'timeOfEvent':
                                return eavrop[key].start + ' - ' + eavrop[key].end;
                            case 'tolkStatus':
                            case 'handelseStatus':
                                return eavrop[key].currentStatus;
                            default:
                                return eavrop[key];
                        }
                    };


                    $scope.testData = {
                        handelse: 'Besök',
                        dateOfEvent: 1241235195,
                        timeOfEvent: {start: '12:00', end: '13:00'},
                        utredaPerson: 'Anna Karlson',
                        role: 'Sjukgymnast',
                        tolkStatus: {
                            currentStatus: 'Bokad',
                            statuses: [
                                {name: 'Bokat', requireComment: false},
                                {name: 'Tolkning genomförd', requireComment: false},
                                {name: 'Tolk avbokad', requireComment: true},
                                {name: 'Tolk uteblev', requireComment: true},
                                {name: 'Tolk anlänt, men tolkning inte använd', requireComment: true}
                            ],
                            comment: null
                        },
                        handelseStatus: {
                            currentStatus: 'Bokad',
                            statuses: [
                                {name: 'Bokat', requireComment: false},
                                {name: 'Genomfört', requireComment: false},
                                {name: 'Patient uteblev', requireComment: true},
                                {name: 'Besök avbokat av utförare', requireComment: true},
                                {name: 'Besök avbokat <96h', requireComment: true},
                                {name: 'Besök avbokat >96h', requireComment: true}
                            ],
                            comment: null
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
                                        var promise = UtredningService.getAllEvents($scope.eavropid);

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
                templateUrl: 'views/templates/fmu-utredning-table.html'
            };
        }]);
