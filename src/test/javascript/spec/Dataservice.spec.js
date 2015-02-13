(function() {
    'use strict';

    describe('Dataservice test', function() {
        var dataservice, backend, urlMock, eavropStates;

        beforeEach(function() {
            module('rest', function($provide, RESTURL) {
                urlMock = {
                    overviewIncoming: RESTURL.overview.incoming
                };

                $provide.value('UrlService', urlMock);
            });

        });

        beforeEach(inject(function(Dataservice, $httpBackend, EAVROP_STATES) {
            dataservice = Dataservice;
            backend = $httpBackend;
            eavropStates = EAVROP_STATES;
        }));

        it("should init Dataservice", function() {
            expect(dataservice).toBeDefined();
            expect(eavropStates).toBeDefined();
            expect(backend).toBeDefined();
        });

        it("should make correct call to eavrop rest api address", function() {
            var fromdate = new Date('October 1, 2014 00:00:00').getTime();
            var todate = new Date('October 1, 2015 00:00:00').getTime();
            var data = dataservice.getIncomingEavrops(fromdate, todate);

            expect(getCalledURl(backend)).toBe(
                '/app/rest/eavrop' +
                '/fromdate/' + fromdate +
                '/todate/' + todate +
                '/status/' + eavropStates.incoming +
                '/page/0' +
                '/pagesize/10' +
                '/sortkey/arendeId' +
                '/sortorder/ASC');
        });

        it("should call ongoing eavrop rest address", function() {
            var fromdate = new Date('October 1, 2014 00:00:00').getTime();
            var todate = new Date('October 1, 2015 00:00:00').getTime();
            dataservice.getOngoingEavrops(fromdate, todate);
            expect(getCalledURl(backend)).toBe(
                '/app/rest/eavrop' +
                '/fromdate/' + fromdate +
                '/todate/' + todate +
                '/status/' + eavropStates.ongoing +
                '/page/0' +
                '/pagesize/10' +
                '/sortkey/arendeId' +
                '/sortorder/ASC');
        });

        it("should call completed eavrop rest address", function() {
            var fromdate = new Date('October 1, 2014 00:00:00').getTime();
            var todate = new Date('October 1, 2015 00:00:00').getTime();
            dataservice.getCompletedEavrops(fromdate, todate);
            expect(getCalledURl(backend)).toBe(
                '/app/rest/eavrop' +
                '/fromdate/' + fromdate +
                '/todate/' + todate +
                '/status/' + eavropStates.completed +
                '/page/0' +
                '/pagesize/10' +
                '/sortkey/arendeId' +
                '/sortorder/ASC');
        });
    });

})();