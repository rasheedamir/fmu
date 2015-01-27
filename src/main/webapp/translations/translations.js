angular.module('fmuClientApp').run(['gettextCatalog', function (gettextCatalog) {
/* jshint -W100 */
    gettextCatalog.setStrings('sv', {"Acceptera-utredning/Acceptera förfrågan om utredning":"Acceptera förfrågan om utredning","Acceptera-utredning/Avbryt":"Avbryt","Acceptera-utredning/Skicka":"Skicka","Alla händelser/Acceptans av utredningen":"Acceptans av utredningen","Alla händelser/Alla händelser":"Alla händelser","Alla händelser/Antal avvikelser":"Antal avvikelser","Alla händelser/Anteckningar":"Anteckningar","Alla händelser/Begärda kompletteringar till utredning":"Begärda kompletteringar till utredning","Alla händelser/Begärda tillägg, handlingar":"Begärda tillägg, handlingar","Alla händelser/Beställning":"Beställning","Alla händelser/Beställning/Acceptans av utredning":"Acceptans av utredning","Alla händelser/Beställning/Enhet/Avdelning":"Enhet/Avdelning","Alla händelser/Beställning/Förfrågan skickad, datum":"Förfrågan skickad, datum","Alla händelser/Beställning/Organisation":"Organisation","Alla händelser/Beställning/Patientens bostadsort":"Patientens bostadsort","Alla händelser/Beställning/Typ":"Typ","Alla händelser/Beställning/Utredare, enhet":"Utredare, enhet","Alla händelser/Beställning/Ärende-ID":"Ärende-ID","Alla händelser/Förfrågan om utredning":"Förfrågan om utredning","Alla händelser/Handlingar":"Handlingar","Alla händelser/Handlingar översändes":"Handlingar översändes","Alla händelser/Intyg skickat till beställaren":"Intyg skickat till beställaren","Alla händelser/Sammanfattning":"Sammanfattning","Alla händelser/Tillägg":"Tillägg","Alla händelser/Utredning":"Utredning","Alla händelser/Utredning antal dagar":"Utredning antal dagar","Anteckningar-tabell/Datum":"Datum","Anteckningar-tabell/Innehåll":"Innehåll","Anteckningar-tabell/Skapad av":"Skapad av","Anteckningar-tabell/Tabort":"Tabort","Anteckningar/Anteckningar":"Anteckningar","Anteckningar/Anteckningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda":"Anteckningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda","Anteckningar/De anteckningar som noteras här skall inte innehålla någon medicinsk eller känslig information.":"De anteckningar som noteras här skall inte innehålla någon medicinsk eller känslig information.","Anteckningar/Fel uppstod vid bortagning av anteckning, detta kan beror på att du inte har rätt behörighet att genomföra denna förfrågan":"Fel uppstod vid bortagning av anteckning, detta kan beror på att du inte har rätt behörighet att genomföra denna förfrågan","Anteckningar/Lägg till anteckning":"Lägg till anteckning","Anteckningar/tabort-modal-kanappar/Ta bort":"Ta bort","Anteckningar/tabort-modal-knappar/Avbryt":"Avbryt","Anteckningar/tabort-modal/Ta bort":"Ta bort","Anteckningar/tabort-modal/Är du säker på att du vill ta bort anteckningen?":"Är du säker på att du vill ta bort anteckningen?","Avslå-förfrågan-modal/Avbryt":"Avbryt","Avslå-förfrågan-modal/Avslå förfrågan om utredning":"Avslå förfrågan om utredning","Avslå-förfrågan-modal/Skicka":"Skicka","Begär tillägg/Avbryt":"Avbryt","Begär tillägg/Begär tillägg":"Begär tillägg","Begär tillägg/Begäran skickad av":"Begäran skickad av","Begär tillägg/Begäran skickad, datum":"Begäran skickad, datum","Begär tillägg/Begäran skickas till":"Begäran skickas till","Begär tillägg/Handling":"Handling","Begär tillägg/Kommentar":"Kommentar","Begär tillägg/Skicka":"Skicka","Beställning/Beställning":"Beställning","Beställning/Handlingar och tillägg":"Handlingar och tillägg","Beställning/Innehåll i beställningen":"Innehåll i beställningen","Beställning/Invånaren":"Invånaren","Beställning/Invånaren/Bostadsort":"Bostadsort","Beställning/Invånaren/Födelseår":"Födelseår","Beställning/Invånaren/Initialer":"Initialer","Beställning/Invånaren/Kön":"Kön","Beställning/Invånaren/Namn":"Namn","Beställning/Invånaren/Personnummer":"Personnummer","Beställning/Invånaren/Sjukskrivande läkare":"Sjukskrivande läkare","Beställning/Invånaren/Sjukskrivande vårdcentral/klinik":"Sjukskrivande vårdcentral/klinik","Beställning/Invånaren/Särskilda behov":"Särskilda behov","Beställning/Invånaren/Telefonnummer":"Telefonnummer","Beställning/Invånaren/Tidigare utredd vid":"Tidigare utredd vid","Beställning/Lägg till handling":"Lägg till handling","Beställning/Lägg till handling/Handling":"Handling","Beställning/Lägg till handling/Registrerad av":"Registrerad av","Beställning/Lägg till handling/Registrerad, datum":"Registrerad, datum","Beställning/innehåll/Beställare":"Beställare","Beställning/innehåll/Beställare, e-post":"Beställare, e-post","Beställning/innehåll/Beställare, namn":"Beställare, namn","Beställning/innehåll/Beställare, tel":"Beställare, tel","Beställning/innehåll/Beställning":"Beställning","Beställning/innehåll/Enhet":"Enhet","Beställning/innehåll/Förfrågan inkom":"Förfrågan inkom","Beställning/innehåll/Tolk":"Tolk","Beställning/innehåll/Typ av utredning":"Typ av utredning","Beställning/innehåll/Val av inriktning":"Val av inriktning","Beställning/innehåll/Ärende-ID":"Ärende-ID","Beställningar/Antal dagar efter förfrågan om utredning":"Antal dagar efter förfrågan om utredning","Beställningar/Antal dagar har överträtts och/eller annan avvikelse finns":"Antal dagar har överträtts och/eller annan avvikelse finns","Beställningar/Beställningar":"Beställningar","Beställningar/Datumen utgår från det datum då beställningen inkommit":"Datumen utgår från det datum då beställningen inkommit","Beställningar/Den försäkrades bostadsort":"Den försäkrades bostadsort","Beställningar/Enhet":"Enhet","Beställningar/Enhet/Avdelning":"Enhet/Avdelning","Beställningar/Förfrågan inkommit datum":"Förfrågan inkommit datum","Beställningar/Organisation":"Organisation","Beställningar/Status":"Status","Beställningar/Typ":"Typ","Beställningar/Visa":"Visa","Beställningar/beställare":"beställare","Beställningar/leverantör":"leverantör","Beställningar/Ärende-ID":"Ärende-ID","Bookning-status/Besök avbokat <48h":"Besök avbokat <48h","Bookning-status/Besök avbokat <96h":"Besök avbokat <96h","Bookning-status/Besök avbokat >48h":"Besök avbokat >48h","Bookning-status/Besök avbokat >96h":"Besök avbokat >96h","Bookning-status/Besök avbokat av utförare":"Besök avbokat av utförare","Bookning-status/Bokat":"Bokat","Bookning-status/Genomfört":"Genomfört","Bookning-status/Patient uteblev":"Patient uteblev","Eavrop-status/Förfrågan accepterade":"Förfrågan accepterade","Eavrop-status/Förfrågan om utredning har inkommit":"Förfrågan om utredning har inkommit","Eavrop-status/Förfrågan tilldelas, inväntar acceptans":"Förfrågan tilldelas, inväntar acceptans","Eavrop-status/Inväntar acceptans":"Inväntar acceptans","Eavrop-status/Inväntar beslut från beställare":"Inväntar beslut från beställare","Eavrop-status/Utredningen godkänts av beställare":"Utredningen godkänts av beställare","Eavrop-status/Utredningen är avslutad":"Utredningen är avslutad","Eavrop-status/Utredningen är påbörjad":"Utredningen är påbörjad","Eavrop-tabell-kompensation-godkänd-status/Ja":"Ja","Eavrop-tabell-kompensation-godkänd-status/Nej":"Nej","Eavrop-tabell-kompensation-godkänd-status/inväntar":"inväntar","Eavrop-tabell-komplett-status/Ja":"Ja","Eavrop-tabell-komplett-status/Nej":"Nej","Eavrop-vyn/Förfrågan om acceptans":"Förfrågan om acceptans","Eavrop-vyn/Invånare":"Invånare","Eavrop-vyn/Till översikten":"Till översikten","Eavrop-vyn/Utse utredare":"Utse utredare","Eavrop-vyn/Utse vårdleverantör":"Utse vårdleverantör","Eavrop-vyn/Utse vårdleverantör genom att hämta in acceptans av denna förfrågan.":"Utse vårdleverantör genom att hämta in acceptans av denna förfrågan.","Eavrop-vyn/acceptera-1/Förfrågan ställd till":"Förfrågan ställd till","Eavrop-vyn/acceptera-2/Acceptans begärd av":"Acceptans begärd av","Eavrop-vyn/acceptera/Acceptera förfrågan":"Acceptera förfrågan","Eavrop-vyn/acceptera/Avslå förfrågan":"Avslå förfrågan","Eavrop-vyn/meny/Alla händelser":"Alla händelser","Eavrop-vyn/meny/Anteckningar":"Anteckningar","Eavrop-vyn/meny/Beställning":"Beställning","Eavrop-vyn/meny/Status":"Status","Eavrop-vyn/meny/Underlag för ersättning":"Underlag för ersättning","Eavrop-vyn/meny/Utredning":"Utredning","Eavrop-vyn/status/Antal dagar sedan utredningens start:":"Antal dagar sedan utredningens start:","Eavrop-vyn/tilldela-1/Inväntar svar från vårdleverantör":"Inväntar svar från vårdleverantör","Eavrop-vyn/tilldela-2/Förfrågan om acceptans för ärende med ID":"Förfrågan om acceptans för ärende med ID","Eavrop-vyn/tilldela-3/skickades till":"skickades till","Eavrop-vyn/Ärende-ID":"Ärende-ID","Genomförda-utredningar/Antal dgr för komplettering":"Antal dgr för komplettering","Genomförda-utredningar/Antal dgr klar":"Antal dgr klar","Genomförda-utredningar/Datumen utgår från det datum då intyg levererats":"Datumen utgår från det datum då intyg levererats","Genomförda-utredningar/Genomförda utredningar":"Genomförda utredningar","Genomförda-utredningar/Godkänd för ersättning":"Godkänd för ersättning","Genomförda-utredningar/Intyg levererades, datum":"Intyg levererades, datum","Genomförda-utredningar/Invänta acceptans av intyg och godkännande för ersättning":"Invänta acceptans av intyg och godkännande för ersättning","Genomförda-utredningar/Typ":"Typ","Genomförda-utredningar/Utredare, ansvarig":"Utredare, ansvarig","Genomförda-utredningar/Utredare, organisation":"Utredare, organisation","Genomförda-utredningar/Utredning komplett":"Utredning komplett","Genomförda-utredningar/Utredning komplett, inväntar godkännande för ersättning":"Utredning komplett, inväntar godkännande för ersättning","Genomförda-utredningar/Utredning är komplett och godkänd":"Utredning är komplett och godkänd","Genomförda-utredningar/Utredningen är ej komplett, ej godkänd, försenad eller innehåller ersättningsbara avvikelser":"Utredningen är ej komplett, ej godkänd, försenad eller innehåller ersättningsbara avvikelser","Genomförda-utredningar/Visa":"Visa","Genomförda-utredningar/avikelser":"avikelser","Genomförda-utredningar/Ärende-ID":"Ärende-ID","Handlingar/Begär tillägg":"Begär tillägg","Handlingar/Handlingar":"Handlingar","Handlingar/Lägg till handling":"Lägg till handling","Handlingar/Tillägg":"Tillägg","Händelse-typ/Besök":"Besök","Händelse-typ/Internt arbete":"Internt arbete","Händelse-typ/Möte med patient":"Möte med patient","Händelse-typ/Okänt handelse":"Okänt handelse","Händelse-typ/Utredningen godkänts":"Utredningen godkänts","Händelse-typ/Utredningens godkänts för utbetalning":"Utredningens godkänts för utbetalning","Händelse-typer/Besök":"Besök","Händelse-typer/Genomgång med patient":"Genomgång med patient","Händelse-typer/Internt arbete":"Internt arbete","Händelser-som-måste-bekräftas/Besök avbokat <96h":"Besök avbokat <96h","Händelser-som-måste-bekräftas/Besök avbokat >48h":"Besök avbokat >48h","Händelser-som-måste-bekräftas/Besök avbokat >96h":"Besök avbokat >96h","Händelser-som-måste-bekräftas/Patient uteblev":"Patient uteblev","Intyg-status/Intyg godkänt":"Intyg godkänt","Intyg-status/Intyg kompleteras":"Intyg kompleteras","Intyg-status/Intyg signeras":"Intyg signeras","Lägg till Händelse/Avbryt":"Avbryt","Lägg till Händelse/Ja":"Ja","Lägg till Händelse/Lägg till Händelse":"Lägg till Händelse","Lägg till Händelse/Nej":"Nej","Lägg till Händelse/Person":"Person","Lägg till Händelse/Roll":"Roll","Lägg till Händelse/Spara":"Spara","Lägg till Händelse/Tolk?":"Tolk?","Lägg till Händelse/Typ av händelse":"Typ av händelse","Lägg till Händelse/Är detta en tilläggstjänst?":"Är detta en tilläggstjänst?","Lägg till anteckning/Anteckning":"Anteckning","Lägg till anteckning/Avbryt":"Avbryt","Lägg till anteckning/Lägg till anteckning":"Lägg till anteckning","Lägg till anteckning/Spara":"Spara","Lägg-till-bokning-Roller/Arbetsterapeut":"Arbetsterapeut","Lägg-till-bokning-Roller/Läkare":"Läkare","Lägg-till-bokning-Roller/Psykolog":"Psykolog","Lägg-till-bokning-Roller/Sjukgymnast":"Sjukgymnast","Lägg-till-bokning-Roller/Utredare":"Utredare","Lägg-till-bokning-misslyckad/Bookningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda":"Bookningen kunde inte skapas, var god och kolla att alla fält är korrekt ifyllda","Pågående-utredningar/Antal dagar från start":"Antal dagar från start","Pågående-utredningar/Antal dagar har överträtts och/eller annan avvikelse finns":"Antal dagar har överträtts och/eller annan avvikelse finns","Pågående-utredningar/Avikelser":"Avikelser","Pågående-utredningar/Datumen utgår från det datum då utredningen startat":"Datumen utgår från det datum då utredningen startat","Pågående-utredningar/Enhet/Avdelning":"Enhet/Avdelning","Pågående-utredningar/Organisation":"Organisation","Pågående-utredningar/Pågående beställningar":"Pågående beställningar","Pågående-utredningar/Status":"Status","Pågående-utredningar/Typ":"Typ","Pågående-utredningar/Utredning start":"Utredning start","Pågående-utredningar/Visa":"Visa","Pågående-utredningar/beställare":"beställare","Pågående-utredningar/leverantör":"leverantör","Pågående-utredningar/Ärende-ID":"Ärende-ID","Tillägg-tabell/Begäran skickad till":"Begäran skickad till","Tillägg-tabell/Begäran skickad, av":"Begäran skickad, av","Tillägg-tabell/Begäran skickad, datum":"Begäran skickad, datum","Tillägg-tabell/Handling":"Handling","Tillägg-tabell/Kommentar":"Kommentar","Tolk-status/Bokat":"Bokat","Tolk-status/Tolk anlänt, men tolkning inte använd":"Tolk anlänt, men tolkning inte använd","Tolk-status/Tolk avbokad":"Tolk avbokad","Tolk-status/Tolk uteblev":"Tolk uteblev","Tolk-status/Tolkning genomförd":"Tolkning genomförd","Underlag-ersättningsvy/Antal avikelser":"Antal avikelser","Underlag-ersättningsvy/Antal dagar efter komplettering":"Antal dagar efter komplettering","Underlag-ersättningsvy/Antal dagar för acceptans av förfrågan om utredning har överskridits":"Antal dagar för acceptans av förfrågan om utredning har överskridits","Underlag-ersättningsvy/Antal dagar för komplettering har överskridits":"Antal dagar för komplettering har överskridits","Underlag-ersättningsvy/Antal dagar för utredning har överskridits":"Antal dagar för utredning har överskridits","Underlag-ersättningsvy/Antal utredningsstarter":"Antal utredningsstarter","Underlag-ersättningsvy/Avvikelse":"Avvikelse","Underlag-ersättningsvy/Besök avbokat <48h":"Besök avbokat <48h","Underlag-ersättningsvy/Besök avbokat <96h":"Besök avbokat <96h","Underlag-ersättningsvy/Besök avbokat >48h":"Besök avbokat >48h","Underlag-ersättningsvy/Besök avbokat >96h":"Besök avbokat >96h","Underlag-ersättningsvy/Besök avbokat av utförare":"Besök avbokat av utförare","Underlag-ersättningsvy/Ja":"Ja","Underlag-ersättningsvy/Nej":"Nej","Underlag-ersättningsvy/Ops detta ska inte hända":"Ops detta ska inte hända","Underlag-ersättningsvy/Patient uteblev":"Patient uteblev","Underlag-ersättningsvy/Specifikation - avvikelser":"Specifikation - avvikelser","Underlag-ersättningsvy/Tillägstjänst":"Tillägstjänst","Underlag-ersättningsvy/Tillägstjänster":"Tillägstjänster","Underlag-ersättningsvy/Timmar":"Timmar","Underlag-ersättningsvy/Tolk":"Tolk","Underlag-ersättningsvy/Tolk anlitad?":"Tolk anlitad?","Underlag-ersättningsvy/Tolk uteblev":"Tolk uteblev","Underlag-ersättningsvy/Typ":"Typ","Underlag-ersättningsvy/Underlag för ersättning":"Underlag för ersättning","Underlag-ersättningsvy/Utförare, namn":"Utförare, namn","Underlag-ersättningsvy/Utförare, organisation":"Utförare, organisation","Underlag-ersättningsvy/Utredning är komplett och godkänd?":"Utredning är komplett och godkänd?","Underlag-ersättningsvy/Utredningen genomfördes på, antal dagar":"Utredningen genomfördes på, antal dagar","Underlag-ersättningsvy/Ärende":"Ärende","Underlag-ersättningsvy/Ärende-ID":"Ärende-ID","Utredning-tabell/Avbryt":"Avbryt","Utredning-tabell/Datum":"Datum","Utredning-tabell/Error":"Error","Utredning-tabell/Händelse":"Händelse","Utredning-tabell/Kommentar":"Kommentar","Utredning-tabell/Person":"Person","Utredning-tabell/Roll":"Roll","Utredning-tabell/Spara":"Spara","Utredning-tabell/Status":"Status","Utredning-tabell/This is an error":"This is an error","Utredning-tabell/Tidpunkt":"Tidpunkt","Utredning-tabell/Tolk":"Tolk","Utredning-tabell/Utredning-tabell/Ändra":"Utredning-tabell/Ändra","Utredningar/Lägg till händelse":"Lägg till händelse","Utredningar/Utredning":"Utredning","Utse-utredare/Begär acceptans om utredning från":"Begär acceptans om utredning från","Utse-utredare/Utse utredare":"Utse utredare","handlingar-tabell/Handling":"Handling","handlingar-tabell/Registrerad av":"Registrerad av","handlingar-tabell/Registrerad, datum":"Registrerad, datum","Ändra-bokning-modal/Avbryt":"Avbryt","Ändra-bokning-modal/Spara":"Spara","Ändra-bokning-modal/kommer att pausa utredningen till dess att beställaren godkänner att utredningen får fortsätta":"kommer att pausa utredningen till dess att beställaren godkänner att utredningen får fortsätta","Ändra-bokning-modal/Ändra":"Ändra","Ändra-bokning-modal/Ändringen till statusen":"Ändringen till statusen","Ändra-bokning-modal/Är du säker på att du vill spara ändringarna?":"Är du säker på att du vill spara ändringarna?","Översikt/Beställningar":"Beställningar","Översikt/Genomförda utredningar":"Genomförda utredningar","Översikt/Pågående utredningar":"Pågående utredningar"});
/* jshint +W100 */
}]);