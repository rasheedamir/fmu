'use strict';
var getCalledURl, mockLogger, routeHelper, getfakeEavrop;
beforeEach(function() {
    getCalledURl = function(backend, response) {
        var calledUrl;
        var PATTERN = /.*/;
        backend.whenGET(PATTERN).respond(function(method, url) {
            calledUrl = url;

            if (response) {
                return response;
            }
            return [200, 'You called: ' + url];
        });

        backend.whenPUT(PATTERN).respond(function(method, url) {
            calledUrl = url;
            return [200, 'You called: ' + url];
        });

        backend.flush();

        return calledUrl;
    };

    mockLogger = function(provide) {
        return provide.value('logger', {
            info: function() {},
            error: function() {},
            warning: function() {},
            success: function() {}
        });
    };

    routeHelper = (function() {
        var retval = {
            setUp: setUp,
            goTo: goTo
        };

        return retval;

        function setUp($rootScope, $location) {
            retval.rootScope = $rootScope;
            retval.location = $location;
        }

        function goTo(url) {
            retval.location.url(url);
            retval.rootScope.$digest();
        }
    })();

    getfakeEavrop = function(eavropId) {
        return {
            'arendeId': '150000000004',
            'eavropId': eavropId,
            'utredningType': 'SLU',
            'creationTime': 1422880073000,
            'status': 'SENT',
            'bestallareEnhet': 'Stockholm, City Servicekontor',
            'bestallareOrganisation': 'Försäkringskassan',
            'mottagarenOrganisation': 'Stockholms läns landsting',
            'utforandeEnhet': 'FMU Enheten',
            'utforandeOrganisation': 'Karolinska',
            'assigningPerson': 'Sam Ordnarsson',
            'nrOfDaysSinceStart': 1
        };
    };
});