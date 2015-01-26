'use strict';

angular.module('fmuClientApp').factory('UtredningService', ['$http', '$filter', '$q', 'RestUrlBuilderService', 'gettext',
    function($http, $filter, $q, RestUrlBuilderService, gettext) {
        var UTREDNING = {
            dateFormat: 'yyyy-MM-dd',
            statusMapping: {
                INTYG_APPROVED: gettext('Intyg-status/Intyg godkänt'),
                INTYG_COMPLEMENT_REQUEST: gettext('Intyg-status/Intyg kompleteras'),
                INTYG_SIGNED: gettext('Intyg-status/Intyg signeras'),
                EAVROP_APPROVED: gettext('Händelse-typ/Utredningen godkänts'),
                EAVROP_COMPENSATION_APPROVED: gettext('Händelse-typ/Utredningens godkänts för utbetalning'),
                UNKNOWN: gettext('Händelse-typ/Okänt handelse'),
                EXAMINATION: gettext('Händelse-typ/Besök'),
                BREIFING_WITH_CITIZEN: gettext('Händelse-typ/Möte med patient'),
                INTERNAL_WORK: gettext('Händelse-typ/Internt arbete')
            },

            handelseMapping: {
                BOOKED: gettext('Bookning-status/Bokat'),
                PERFORMED: gettext('Bookning-status/Genomfört'),
                CANCELLED_NOT_PRESENT: gettext('Bookning-status/Patient uteblev'),
                CANCELLED_BY_CARE_GIVER: gettext('Bookning-status/Besök avbokat av utförare'),
                CANCELLED_LT_48_H: gettext('Bookning-status/Besök avbokat <48h'),
                CANCELLED_GT_48_H: gettext('Bookning-status/Besök avbokat >48h'),
                CANCELLED_LT_96_H: gettext('Bookning-status/Besök avbokat <96h'),
                CANCELLED_GT_96_H: gettext('Bookning-status/Besök avbokat >96h')
            },

            tolkMapping: {
                INTERPRETER_BOOKED: gettext('Tolk-status/Bokat'),
                INTERPRETATION_PERFORMED: gettext('Tolk-status/Tolkning genomförd'),
                INTERPRETER_CANCELED: gettext('Tolk-status/Tolk avbokad'),
                INTERPRETER_NOT_PRESENT: gettext('Tolk-status/Tolk uteblev'),
                INTERPRETER_PRESENT_BUT_NOT_USED: gettext('Tolk-status/Tolk anlänt, men tolkning inte använd')
            },

            eventsRequireConfirmation: {
                CANCELLED_GT_48_H: gettext('Händelser-som-måste-bekräftas/Besök avbokat >48h'),
                CANCELLED_NOT_PRESENT: gettext('Händelser-som-måste-bekräftas/Patient uteblev'),
                CANCELLED_LT_96_H: gettext('Händelser-som-måste-bekräftas/Besök avbokat <96h'),
                CANCELLED_GT_96_H: gettext('Händelser-som-måste-bekräftas/Besök avbokat >96h')
            },

            handelseTypes: [{
                type: 'EXAMINATION',
                name: gettext('Händelse-typer/Besök')
            }, {
                type: 'BREIFING_WITH_CITIZEN',
                name: gettext('Händelse-typer/Genomgång med patient')
            }, {
                type: 'INTERNAL_WORK',
                name: gettext('Händelse-typer/Internt arbete')
            }],

            roles: [{
                name: gettext('Lägg-till-bokning-Roller/Läkare')
            }, {
                name: gettext('Lägg-till-bokning-Roller/Psykolog')
            }, {
                name: gettext('Lägg-till-bokning-Roller/Arbetsterapeut')
            }, {
                name: gettext('Lägg-till-bokning-Roller/Sjukgymnast')
            }, {
                name: gettext('Lägg-till-bokning-Roller/Utredare')
            }],


            errors: {
                cannotCreateBooking: gettext('Lägg-till-bokning-misslyckad/Bookningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda')
            }
        };

        function getTimeHHMM(hour, minut) {
            var hh = hour < 10 ? '0' + hour : hour;
            var mm = minut < 10 ? '0' + minut : minut;
            return hh + ' : ' + mm;
        }

        function getTextConstants() {
            return UTREDNING;
        }

        return {
            getTextConstants: getTextConstants,
            getAllEvents: function(eavropId) {
                return $http.get(RestUrlBuilderService.buildEventsRestUrl(eavropId)).then(function(data) {
                    // Success
                    return data.data;
                }, function(err) {
                    // Failed to retrieve data
                    return $q.reject(err.data);
                });
            },

            createBooking: function(booking) {
                return $http.post(RestUrlBuilderService.buildCreateBookingRestUrl(), booking)
                    .then(function(data) {
                        // Success
                        return data.data;
                    }, function(err) {
                        // Failed to retrieve data
                        return $q.reject(err.data);
                    });
            },

            changeBooking: function(dataPackage) {
                return $http.post(RestUrlBuilderService.changeBookingRestUrl(), dataPackage)
                    .then(function(success) {
                        // Success
                        return success.data;
                    }, function(error) {
                        // Error
                        return $q.reject(error.data);
                    });
            },

            changeTolkBooking: function(dataPackage) {
                return $http.post(RestUrlBuilderService.changeTolkBookingRestUrl(), dataPackage)
                    .then(function(success) {
                        // Success
                        return success.data;
                    }, function(error) {
                        // Error
                        return $q.reject(error.data);
                    });
            },

            getTableFields: function() {
                return [{
                        key: 'handelse',
                        name: gettext('Utredning-tabell/Händelse'),
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'dateOfEvent',
                        name: gettext('Utredning-tabell/Datum'),
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'timeOfEvent',
                        name: gettext('Utredning-tabell/Tidpunkt'),
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'utredaPerson',
                        name: gettext('Utredning-tabell/Person'),
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    }, {
                        key: 'role',
                        name: gettext('Utredning-tabell/Roll'),
                        restricted: ['ROLE_UTREDARE', 'ROLE_SAMORDNARE']
                    },

                    {
                        key: 'tolkStatus',
                        name: gettext('Utredning-tabell/Tolk'),
                        restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                    }, {
                        key: 'handelseStatus',
                        name: gettext('Utredning-tabell/Status'),
                        restricted: ['ROLE_SAMORDNARE', 'ROLE_UTREDARE']
                    }
                ];
            },

            getTableCellValue: function(key, rowData) {
                var celldata = rowData[key];
                if (celldata === null) {
                    return '-';
                }
                switch (key) {
                    case 'timeOfEvent':
                        var endTime = rowData.timeOfEventEnd;
                        if (endTime !== null) {
                            return getTimeHHMM(celldata.hour, celldata.minute) + ' - ' + getTimeHHMM(endTime.hour, endTime.minute);
                        }

                        return getTimeHHMM(celldata.hour, celldata.minute);
                    case 'dateOfEvent':
                        return $filter('date')(celldata, UTREDNING.dateFormat);
                    case 'tolkStatus':
                        if (celldata.currentStatus === null) {
                            return '-';
                        }
                        return UTREDNING.tolkMapping[celldata.currentStatus.name];
                    case 'handelseStatus':
                        if (celldata.currentStatus === null) {
                            return '-';
                        }
                        return UTREDNING.handelseMapping[celldata.currentStatus.name];
                    case 'handelse':
                        return UTREDNING.statusMapping[celldata];
                    default:
                        return celldata;
                }
            }
        };
    }
]);