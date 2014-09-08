describe("End to end tests", function() {
    var protractorInstance, testPage;
    protractorInstance = protractor.getInstance();
    var homePage = function(){
        this.notLoggedInMessageElement = element(by.id("notAuthenticated"));
        this.authenticated = element(by.id("authenticated"));

        this.get = function(){
            browser.get("http://localhost:8080/");
        };
    };

    describe("Home page tests", function(){
        beforeEach(function(){
            testPage = new homePage();
        });

        it("should only show not logged in message", function(){
            testPage.get();
            expect(protractorInstance.isElementPresent(testPage.notLoggedInMessageElement)).toBe(true);
            expect(protractorInstance.isElementPresent(testPage.authenticated)).toBe(false);
        });

        it("should show logged in message after authentication", function(){
            testPage.get();

        });
    });
});