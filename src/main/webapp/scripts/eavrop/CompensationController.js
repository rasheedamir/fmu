'use strict';

angular.module('fmuClientApp')
    .controller('CompensationController', ['$scope', '$filter', 'AuthService', 'currentEavrop', 'EavropService', 'EAVROP_COMPENSATION',
        function ($scope, $filter, AuthService, currentEavrop, EavropService, EAVROP_COMPENSATION) {
            $scope.authService = AuthService;
            $scope.currentEavrop = currentEavrop;

            $scope.getArendeData = function (key, data) {
                var value = data ? data[key] : '-';
                switch (key){
                    case 'tolkBooked':
                    case 'isCompletedAndApproved':
                        return EAVROP_COMPENSATION.jaNejMapping[value];
                    default :
                        return value ? value : '-';
                };
            };

            $scope.getAvikelserData = function (key, data) {
                return data ? EAVROP_COMPENSATION.statusMapping[data[key]] : '-';
            };

            $scope.getTillaggData = function (key, data) {
                var value = data ? data[key] : '-';
                switch (key){
                    case 'antalTimmar':
                        return millisToHours(value);
                    case 'tolkBooked':
                        return EAVROP_COMPENSATION.jaNejMapping[value];
                    default :
                        return value ? value : '-';
                };
            };

            function millisToHours(milliseconds) {
                return ((milliseconds / (1000*60*60)) % 24);
            }

            var initData = function() {
                return currentEavrop.$promise.then(
                    function () {
                        // Init table fields
                        if(!$scope.arendeHeaderFields){
                            $scope.arendeHeaderFields = [
                                {
                                    key: 'arendeId',
                                    name: 'Ärende-ID'
                                },
                                {
                                    key: 'utredningType',
                                    name: 'Typ'
                                },
                                {
                                    key: 'utforareOrganisation',
                                    name: 'Utförare, organisation'
                                },
                                {
                                    key: 'utforareNamn',
                                    name: 'Utförare, namn'
                                },
                                {
                                    key: 'tolkBooked',
                                    name: 'Tolk anlitad?'
                                },
                                {
                                    key: 'utredningDuration',
                                    name: 'Utredningen genomfördes på, antal dagar'
                                },
                                {
                                    key: 'nrDaysAfterCompletetion',
                                    name: 'Antal dagar efter komplettering'
                                },
                                {
                                    key: 'nrAvikelser',
                                    name: 'Antal avikelser'
                                },
                                {
                                    key: 'nrUtredningstarts',
                                    name: 'Antal utredningsstarter'
                                },
                                {
                                    key: 'isCompletedAndApproved',
                                    name: 'Utredning är komplett och godkänd?'
                                }

                            ];
                        }

                        if(!$scope.specificationHeaderFields){
                            $scope.specificationHeaderFields = [
                                {
                                    key: 'deviationType',
                                    name: 'Avvikelse'
                                }

                            ];
                        }

                        if (!$scope.tillaggtjanstHeaderFields && currentEavrop.utredningType == 'AFU') {
                            $scope.tillaggtjanstHeaderFields = [
                                {
                                    key: 'name',
                                    name: 'Tillägstjänst'
                                },
                                {
                                    key: 'antalTimmar',
                                    name: 'Timmar'
                                },
                                {
                                    key: 'tolkBooked',
                                    name: 'Tolk'
                                }
                            ];
                        }

                        return currentEavrop.eavropId;
                    }
                );
            }, fetchRestData = function (eavropId) {
                return EavropService.getCompensation(eavropId).then(function (serverData) {
                    $scope.tableData = [serverData];
                    $scope.specifications = serverData.avikelser;
                    $scope.tillaggTjanster = serverData.tillaggTjanster;
                    return serverData;
                });
            };

            initData().then(fetchRestData);
        }

    ]);
