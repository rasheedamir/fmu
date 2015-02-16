(function() {
    'use strict';

    describe('Dataservice test', function() {
        var dataservice, backend, urlMock, eavropStates, restUrl;

        beforeEach(function() {
            module('fmu.core');

        });

        beforeEach(inject(function(Dataservice, $httpBackend, EAVROP_STATES, RESTURL) {
            dataservice = Dataservice;
            backend = $httpBackend;
            eavropStates = EAVROP_STATES;
            restUrl = RESTURL;
        }));

        it("should init Dataservice", function() {
            expect(dataservice).toBeDefined();
            expect(eavropStates).toBeDefined();
            expect(backend).toBeDefined();
            expect(restUrl).toBeDefined();
        });

        it("should make correct call to eavrop rest api address", function() {
            var fromdate = new Date('October 1, 2014 00:00:00').getTime();
            var todate = new Date('October 1, 2015 00:00:00').getTime();
            var data = dataservice.getIncomingEavrops(fromdate, todate);

            expect(getCalledURl(backend)).toBe(
                restUrl.overview.incoming
                .replace(':fromdate', fromdate)
                .replace(':todate', todate)
                .replace(':status', eavropStates.incoming)
                .replace(':page', 0)
                .replace(':pagesize', 10)
                .replace(':sortkey', 'arendeId')
                .replace(':sortorder', 'ASC'));
        });

        it("should call ongoing eavrop rest address", function() {
            var fromdate = new Date('October 1, 2014 00:00:00').getTime();
            var todate = new Date('October 1, 2015 00:00:00').getTime();
            dataservice.getOngoingEavrops(fromdate, todate);
            expect(getCalledURl(backend)).toBe(
                restUrl.overview.incoming
                .replace(':fromdate', fromdate)
                .replace(':todate', todate)
                .replace(':status', eavropStates.ongoing)
                .replace(':page', 0)
                .replace(':pagesize', 10)
                .replace(':sortkey', 'arendeId')
                .replace(':sortorder', 'ASC'));
        });

        it("should call completed eavrop rest address", function() {
            var fromdate = new Date('October 1, 2014 00:00:00').getTime();
            var todate = new Date('October 1, 2015 00:00:00').getTime();
            dataservice.getCompletedEavrops(fromdate, todate);
            expect(getCalledURl(backend)).toBe(
                restUrl.overview.incoming
                .replace(':fromdate', fromdate)
                .replace(':todate', todate)
                .replace(':status', eavropStates.completed)
                .replace(':page', 0)
                .replace(':pagesize', 10)
                .replace(':sortkey', 'arendeId')
                .replace(':sortorder', 'ASC'));
        });

        it("should make call to get an eavrop with a specific EavropID", function() {
            var eavropId = 'testId';
            dataservice.getEavropByID(eavropId);
            expect(getCalledURl(backend)).toBe(restUrl.eavrop.replace(':eavropId', eavropId));
        });

        it("should make call to get the corresponding patient based on eavropId", function() {
            var eavropId = 'testId';
            dataservice.getPatientByEavropId(eavropId);
            expect(getCalledURl(backend)).toBe(restUrl.eavropPatient.replace(':eavropId', eavropId));
        });
    });

})();