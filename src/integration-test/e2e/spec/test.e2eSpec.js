describe("End to end tests", function() {
    var protractorInstance, testPage;
    protractorInstance = protractor.getInstance();
    var homePage = function(){
        this.notLoggedInMessageElement = element(by.id("notAuthenticated"));
        this.authenticated = element(by.id("authenticated"));

        this.get = function(){
            browser.get("http://localhost:9000/");
        };
    };

    beforeEach(function(){
        testPage = new homePage();
    });

    it("should only show not logged in message", function() {
        testPage.get();
    });

});