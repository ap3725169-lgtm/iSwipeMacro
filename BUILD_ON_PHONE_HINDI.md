# मोबाइल से APK बनाने का तरीका (GitHub Actions)

यह प्रोजेक्ट अभी शुरुआती प्रोटोटाइप है। यह अपने रिकॉर्डिंग पैड पर gesture रिकॉर्ड करता है और Android Accessibility Service से gesture replay का परीक्षण करता है। यह Free Fire के अंदर रिकॉर्डिंग/गेम मैक्रो का तैयार समाधान नहीं है।

## फोन से build करने के चरण
1. ZIP को फोन में डाउनलोड करके Extract करें।
2. Chrome में https://github.com खोलें और अपने खाते में लॉग इन करें।
3. New repository बनाएं, नाम `iSwipeMacro` रखें और Public या Private चुनें।
4. Extract किए गए फ़ोल्डर के अंदर की सभी फ़ाइलें और `.github` फ़ोल्डर भी repository में upload करें। ZIP फ़ाइल को अकेले upload न करें।
5. Repository में Actions टैब खोलें। `Build iSwipe Macro APK` workflow चुनें।
6. `Run workflow` दबाएं।
7. Build पूरा होने पर नीचे Artifacts में `iSwipeMacro-debug-apk` डाउनलोड करें।
8. ZIP artifact को Extract करें। उसमें `app-debug.apk` मिलेगा। उसे इंस्टॉल करने के लिए Android की अनुमति देनी पड़ सकती है।

## जरूरी
- GitHub account और इंटरनेट चाहिए।
- Build में error आए तो Actions के लाल error log का screenshot भेजें।
- Android Studio मोबाइल पर नहीं चलता; build GitHub के cloud computer पर होगा।
- ऐप की मौजूदा रिकॉर्डिंग केवल अपने drawing pad में होती है; दूसरे ऐप में सभी touch events को चुपचाप रिकॉर्ड करना समर्थित नहीं है।
