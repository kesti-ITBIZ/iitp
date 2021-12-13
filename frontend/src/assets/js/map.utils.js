let kakao = null;

export default class KakaoMapUtils {
    /**
     * @author Lee Jae Yeol
     * @param dom : HTMLElement - 맵 DOM
     * @description Kakao Map 초기화
     * */
    constructor(dom) {
        this.map = null;
        this.markers = null;

        const mapInit = async () => {
            kakao = window.kakao;
            this.markers = [];
            this.map = new kakao.maps.Map(dom, {
                center: new kakao.maps.LatLng(36.020826, 127.0236867),
                level: 12
            });
            this.map.addOverlayMapTypeId(kakao.maps.MapTypeId.TERRAIN);

            await new Promise(resolve => setTimeout(resolve, 0));
            this.map.relayout();
            await new Promise(resolve => setTimeout(resolve, 10));
            this.map.setCenter(new kakao.maps.LatLng(36.020826, 127.0236867));
        };

        window.kakao && window.kakao.maps ? mapInit() : (() => {
            kakao = window.kakao;

            const script = document.createElement("script");
            script.onload = () => kakao.maps.load(mapInit);
            script.src = "http://dapi.kakao.com/v2/maps/sdk.js?appkey=5baf2658eb6ff4b1384697fbc66d2030&libraries=services,clusterer,drawing";
            document.head.appendChild(script);
        })();
    }

    /**
     * @author Lee Jae Yeol
     * @param id : string - 맵 타입
     * - ROADMAP : 일반지도
     * - SKYVIEW : 스카이뷰(위성지도)
     * - HYBRID : 하이브리드(스카이뷰 + 레이블)
     * - ROADVIEW : 로드뷰
     * - OVERLAY : 레이블
     * - TRAFFIC : 교통정보
     * - TERRAIN : 지형도
     * - BICYCLE : 자전거
     * - BICYCLE_HYBRID : 스카이뷰를 위한 자전거 (어두운 지도에서 활용)
     * - USE_DISTRICT : 지적편집도
     * */
    setMapType(id) {
        this.map.setMapTypeId(kakao.maps.MapTypeId[id]);
    }

    /**
     * @author Lee Jae Yeol
     * @param latitude : number - 위도
     * @param longitude : number - 경도
     * @param content : string - 마커 툴팁 내용에 들어갈 HTML Markup Text
     * @param markerImgUrl : string - 마커 이미지 URL
     * */
    addMarker(latitude, longitude, content, markerImgUrl) {
        const marker = new kakao.maps.Marker({
            map: this.map,
            position: new kakao.maps.LatLng(latitude, longitude),
            image: new kakao.maps.MarkerImage(markerImgUrl, new kakao.maps.Size(18, 18))
        });

        let infoWindow = new kakao.maps.InfoWindow({ content });

        kakao.maps.event.addListener(marker, "mouseover", () => infoWindow.open(this.map, marker));
        kakao.maps.event.addListener(marker, "mouseout", () => infoWindow.close());

        this.markers.push({ marker, infoWindow });
    }

    /**
     * @author Lee Jae Yeol
     * @param data : { latitude: number, longitude: number }[] - 위/경도 데이터 리스트
     * */
    clustering(data) {
        if (this.clusterer != null) this.clusterer.clear();
        this.clusterer = new kakao.maps.MarkerClusterer({
            map: this.map,
            averageCenter: true,
            minLevel: 10,
            texts: value => value + "곳"
        });
        this.clusterer.addMarkers(data.map(obj => new kakao.maps.Marker({
            position: new kakao.maps.LatLng(obj.latitude, obj.longitude),
            image: new kakao.maps.MarkerImage(
                "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAIcAAACICAYAAAG6tshJAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAAFxEAABcRAcom8z8AADErSURBVHhe7X0HnF1Vtb4KCkoboyKiqFj+4nvqe7aHPh+Wv/oePLFQpUMCBCQUadIFTEARBBI6iEgXUaSFIkggdZLJJJlMpqVOkilpkz69nPd966x9zj7n7HPb3DszQb75rd/ce+7ue52911577bXflgkDAwP7gtpAlaDd9HFu8BSVF37Fm3FsmdDqp3+nTz1Pg7mB3PoZaMZx781Iit01Wggk0NuzaW0k8KLxP9TwTHhU5DdCo4bwA0YD8f/Uw9/lTTvqPd70o3f3etu3JMJo9GQC03+6h0SU52iPaUfsIp9XPXVDJBwJNdjuTMT/XubNPP598pno3bJOfltw+bcTYdkWey+4/FuRH3o2+22z7IGLvf6e7rBUeMZq2WEJJtIq3Wj9QFrzjwe92ad9TAIRM44f5f+Phevv7ZZE6mYcu1fix9rfHCURDX9I2xy9WyLcQH+fx/Yoa7h9bOJHko3+rvbUMM6GtQO4PttEpCaCvkMV9pJABHtr/i++HglTPuaj7OIZkgjhB7QSESChyPdkGI3uAyleEQ+UiRIJGCChs/2Ekl0ekFZRo6RDQgF9HVu92afv7y245L+8gb5eeYaMejVYbkCEB5gW/o/RR7lBsgO625qEwQyxtwgk+GMN6gYDZWwPUPvKGklMo0ThJxCNEKC/P/J8Otif0Kg++MB+CafhTWWj8vOs0R+W/xJGfyc1TBzNqjVoEslSyHckuql6Svjd+m+HcyZAWj/9zxJg2uHvlP8mAmGHqx5/KEvzHWcinesagwjm/+qnfht8tolIbVD+X/aHC7wFl31T26vM27Z0fiScCetMhLRtaaUEiL7JyXBEaiLsxoqz/0UC9W7f6AxDIvxEjnX/OPecL8jnDXOe9da89nAiTOOjV7Fh90vtoe0rqrxlf7xEEpt10t7yPx6GkAQIfrGngvamOgkw/af+s8qf/7t8txly27J5LMVFmoQPCaQBshEnMEKjRsEf1ryerLtNBhrFDRTxDA3nLbn3HK8cE9i6aU/okyyRcwEyKNe0cgLCH6xRBwckNEHTFMw6aR9nM7lo+jF7eI2PXKUxfWiyuUPjCWzOGAzNOeNTmqK01Ic1KzcQQKZAwpWY0DHhK70Zw17F2f/qlWP8XHDZt/Sp53W0LnfHJVlDgmYbBQoh8uvME/d2JwAykO8qNdno69ouv5mZYOHV34/Et8lAs/eB76/zYfmY/ZyRSNsbF4poxs9E7zZ/jJp7zue99lU12oWYJIF5F35VJFhi6mE7B2nEyUCL4fMEx3ZXYENEywt34LOfGTOaqpOJAcNtqZkafCaqf/W/QRpxmq7dzN5gl9zOL9OP2dMZ2FD11QdLJH6u/93xwWeD7Y1VQcJMa97FB8pnOw0XoRQSjgWRT9lECVLFOH/qqLkBAjK+E2tefSD4bGpvkEuaPZvXSFgWRKYDNrUrYBr193R5fZ1bJREb0456tzN8GhkEPNIXW0tlopYX75bIRNPk2+UZlwcG8UVMGk3XhQ8ao14Kgg/X8YHIDo4INs0+/eMS2aB+0mhZSenbH8AVN04GUggDFOZVPlxyz9nOSKRpR+4qETkvG1C6rp94in7zvE0Lp3jtq/3525WGIQPNPgoUxl+uAi5GIzpalgYCQhpxUCTia1LSout+Ir8Rmm06UKCnNCxaIT/mS6M1//ijpphDAVzQuIKmZ24BI77HmVGcZp74AY3lg12vSQ4eSOzTIH8EygIEm6TRSgdkshtoDIhSNldzVDM9ANpXg5QOyMQf43MEwpdr1OIAaYZsrmh+/jZ/to0RxeyuDas1VAhNqnBoOoKVT16f1zQw84T3a0wfaKHrNNncgXjBiNXRusyZUa5k5BIChenXLLIDgT+s8WS2dSVeCGEy0lRz7CoNK1K4K0ESZ9iutiYNGWLJ3ePwe3JENtS5ZrmEQ2UzK1skFDAT8qgrIdKG2c9qKMik1x4s4uWcn30GIyf1Mj6omHDFJQ3gj0BhLtdso8APDQyw9P4LnAmQttTPlETKT/2ofOd0P+vkfSLTQMeqWgmTKvHh7TLQrKPQ39yRQTODZbTfWt0bW+S7jcqff9H/ra1ZvtvxbZquS4oE8+LB9kwRScT2ldXyeQDSPGELQJyHiIpzPx9kVH3tIcHvcTLQIvjQZ84IpHBp8E5RRhH+mFLmbV0616u64jsSzvAJP3OJAWElSCNOM08MxpnXpRBoje/wW92tJzkjkKYd4QtE/Lxp/t+Dz0blSExH37OFCPIB9VLy2UonTgamIPKCZx41fQbj546WJcFnG7NO/lDQclxWzrv4a/7nSDpRam+ulzBSEPkEuAIaCkREyCKVF3xZPstveDMEqAu/V5x1gHzl5w2znwk+p9Gcn/0/CYPG2DungpCI5Q9eEnxm7TfOf0U+E2wFdtXaKb6mh6BUZqcRJ77iBApyuxSESwFXQJuW3HeuRGL/m3GDMIIz35qZJ31Qnhu5NPsCy+9yFKQ154KQDFiYeRf+h/8Zz5fdf37wefbYT8jnOixL7bhpRKAgA37XaB9nJR0VjdqBoES/7A8Xyme+2uyOpmcnuuPHSdNDOepy5hGbzNbUQG+P/Cd6NmI05S6LI3waWTxySUEFadf5hBkbrHjkSvlPVYUrjousfcgyjiPyMnPQcgVO0LH6yirYvFTWGFCh44znIG4CEjKOoCD78cvqv/3OGThONTccLZEJFsI8n35UyDfZ3xafDKQghH53Bo5THOznmusP028+ltx1ljOuTfN0YERDhLIsvszgw2yi4TSMrAT/2xuzxOqnb5JC2RNfJjLQIoTQ585Ihvo6t3n9XR3oxps0NKT7J8ajr9fpN1RGNyRmjd7XmQap9rd+96IBXtHsQ+DhRfwxTWo3r5oBu8P+na901/qV+qsP+3dDZoYmNOsk9HcZtuMJcPgmqq85OPFbhMDAbRXPS1jX7wao+C6arRsaTt5xV0KDIQMUwi04x6HhvfXlTzsTzJeMGEGgEH/VbHKDxhNQBnVlkAs1TxY1rgCFGKfJ5wdEFL2awaxTPuTMLE5kyFV//Y3G8qFJDg4oUCicAhSA1s/8q7fgsoNkd2r2aR/3FmOVR3MTB8ZqMsUDCjRJE88IhCM+rdGGHyjTzijQIaDJfhELB9NgWvi4syY/8oECXwySReNQgHkxT81++IHC7AfyZRsHNtdMw/T2U5HZuJPjetFzIoxiTINrg00LX9PUk2BZWCYt3tABmYoiJY6mZ25FBUZJBZwVKyaJaFjmNf5pvOYeBcuoxS0NkMG+mlcEy+6/AHJI0sZsqIlrq4bbT9NSRcGyazUGD6S3OxKM7AH0dW73Zp7wPmfBciEKetQFUxGw+M4zvdXP3Ow1PzcRi8WLZLuYGjKGyXUh4KKeLeu1tD5QhX5QftaocSCBak1P0Lt9k6xcXAXIRKzcwmv+x+vv7tSU8sOyBy4qiAupr+je5G/kGqBOC7V6uQPxEkso9qYr00xENu5oXqwp+Gh+8U5wx6jMg6yOQfW3niy6EoP+ns5k2ByI9oUOvFermxlowe9qBEFH6xIRel0ZpRE1iyseulxT8De1bSUh1z6Lfn2Yt6V+loYI0dFU73OElefUI3YR0zyDrQ2z9ZWK5puJuMm9bfkCTcEH66rVdgMBjtOwgrWvP+JMPBttWzZfU/C8BZd8I3ge3zwnutat8lr//nuv6blJ3vaViyIqW6L2pmN0RqHBYziu0yRh+k/zf41WqXWhAeus1Y8CP3xJwwianr0lKEg+1PinX2kKnrfwl75BD3d81kHWN6BpA7kkHjcgvDKLrvuxhvZtdsyeu73y3tpQnjencKBe8dClmoIP1l2bwQeevcP/ycfWxXNkZeROMJ1oR28Gzm0rqlAJv7B1N4WMN/fczwfhl6rC1kbr3+8LOeJkf7VPUMgzsw4VMAZcSJn0ciV20MZ5L2sKAd6hzSHc8bA+FExDxVwJZSPbBoC28PIclbNHeupl+TxloBNU/fJ7Eoa93zb3BX3qCefw+dyff1GfeOCkH/n55Em0APOsAZttII2Bz6GaAGh99X5nArmQZKLYtODV4HnLS/fqU+6gqIkjGqr1lfv1aYhNVf8IFA5mp4UY6OkOOKfhtjH6lAdWvhrkky+t+vP1mkqAXckdR+oXQSGvSkCoCCtkEIjwYFEbRqjj9lIcc8/1TYnZKL0doR3XLLUT5OzTq0IXOS/fGdAmjks22BZskOn6nU/CShRIPCoy0B/ut5vplplvXPCKmD5LOOqvdF++o6nB21IbFsPYXTc+fo3X274ZU6a/IcTXbdvycAajtMvnBRPqapeVbcEGCfY2ZMOogJklTnz3adlpUHfjTyO/8+wRZRNioLcLcsauIgFTgSzPMPXGbZfKxwS2EgKOV/bvhVGZzGAGbAs2SMCXxWoQQ7VoCBvlYz6C9Pf0tloCWYQjwRkGXIuwYTlzkXMNWl66R2YJO5/Cqczr6wjVfGwLNsjf9LtkPCi9hZPKvIpzPufVXP8T+W6OXdjgNG8beRhsqZshcWahIWsg2eYvc2QhdIZ5bQm2BRvkC/pdkMnSerC0aEIobK149CrbKiFA3e+O82puCMf5VX++zplWMYgNbINtwWn37f5XH1vqZjojD5bmnBka45uxwmBT9eve4rvO1G8Kq+cy2ZwMhjbo1o2Ft4ssgpYZpw8Es8fu70ygYHLY7ROUYF1jVtf6VRoiRCbz/ELIYZsX3STBg0Cj0t/bIzOBK6G8CRUe6As36QkuzGhb45riKX9UXfFt0b3EYZYCgyWRZbZu0FSlMdZrM4TA88ir07V+9eAHWDTG1vrwEBA3kDlLuOBSHq157UHhLqMTYSUyLgpzII4btA+wger7r0oc+C1y+qJvO4QiXWUWQo1PhAeTuHolR8w8/v2yOW0rfhLAbNc2d7Is92VmsV45nt2xdSt5EdKKa9CAd2r13QD7vFsD+sDgZkyE8qGqy8ODRj3b2vSTBaRLo07brISvKRvOWBLasA2EKcHmLy/h1bWERYJ11WpnBwJHTJTFOijHQphNehuUCKuu+q5z3EglhOVgSkvIOBZe/d/uOAkq8xofCQ6QCVC3Jq1mfkBc/zSjBW4Wuc54xok2PfN/8Z+DWnzFiQMuV8uV538pp86puvL/a6lDoDG+pdUrHEjkMU0vwOK7fjao8aVURLGeBndxoA6PanWKByR6p6YfoGfrerH8cxVuKGnWKfs6xx6U+Q4tfumATD4HimqDFdx4kpkh70EvD0La5M76W07SXKNg2VhGLe7QAhl/XcvhROfaFeJiZBYGWv8Vy6+hZIo94X2Yqn/ldbQu1VTdYFm0WCMHKNSZoCTvFhnMA3SGZrtjAQXfG/Rd0BOgDBJZFAyrcRh3b03uzQHUj9ZEo0AHgcaDXgat6O/v78T/Af2/Qp/zd4ajiLrjWAxlAipyACp0BSghK+cDxu/r66NkdYAmvWMABSYHTAIVtsWfBUyX6ePjTprlyAMKtxcKGV06lhjMD7SnFmH4wcKAotvoMdAbD/dd6yee7G8VUCbJRseNEvXh5tppsvWQCci/atgbBQXwTz040NfV7jU9P9Erh7Q4WH2FGNOjEWnLnqlhWB4t2tAB+b4HGSdP/AGdLUu8hVd9L7J8LyZx+4Gn59sbkytdguUCDc5EKlcgo9CvngW+CtxqHKpFHfOpGHdAZDPJBsp5qBa5NOjt7Q2NOywsve88fc/dBS8pId/6iaNR+1ALb+EaLXpxgZZOLO+5bhj03mmxCI3S7lASodzFXdIjQf8QpYXWV37vFctZRbGI5bF9cxqg/E9rVQYHJHSXphlg8Z1jC9eyUy2IXqR7q4XXHuKteOxqr3nyHd6qJ6/36m46xps1+iOiKB5M+vF9YgL1uFOrVBiQAF7GKOhlqJDxgTqP2Wd8MuIKJxNoNzLv/C8XOC2XeYsmJMd51kerlh86Ozv9w6wWGm47Tcwn3QVIJ9porJseum/LB/RYl+kcXCaq+e1RmkoINMj+WsXcgUiR4Xnln8f7LO7INBNV/OwA2XGzwR255Q9dKoeKqUakGo8OW+mYgEa33W1JEcZ1lC4roeM409lgvbSKuQERXtK4gq2Laeya7wZPWeBG0ICGLnPGfdYfDzJwGF8pbkcYN54GtBMVFaMjThoxPDeubLB+WtXMQFj/NKpCnNkWMJhRALPRNPk2EauDMGgM2WJAYbl3wima0mWkso7BcMm9Z+fNoRTQHOfXvqxVTgdaLRJr/i9CK+J8yN5mXHznGbFKlslA2mNtFhOMs6H8aW/OmZ+OVJh7KDYq+LtJK0eyz20TqOdWrbIbCBA517x50euBaWPOhIq2zQvfspU0VAleiTJvxaOpa7sINs1/JcKR8y/9hv7ib2hnes2chPA88WiD9dWqJ4EfIzrKfN9Pkm1wK7ZdgeXgHl5b5Yv6iw+uZzZVveatfuZ3KOhfEudSuje1BmsdjjO0YzeopT2IlW8uxPrYYH216lHgh8jE7B/vcieaiTaqrxSCXkLkObiLO/QhBsRGjCcK7LgsLJ0A9WwJj5lvweBtzrZwWkcN5Dk3tIwVcz60XL3XGrDe2gQh8DCyEV2IiSIHQONtomvtisA3Aw/iGtCGk8a18bg2xQ/pBL4q0RhrpjykTz1vtjoxypdssN7aBD7wbB//Jx9r33i0oFdk7rn/pil44u1CnoMr6ITYoPKCr4Rx8Btfkc41KwJXlQFZthv9vV1BeeyBcPnDl0fj5ELIk0dGYthHm0K4Qpw6GsgOuCuhLESn0gYyI/A5WVshPlt1QKaZN08xRQA5L9h9Q7yWl+/TH8CpJ/kWjFOPAPf1+UPbxrkvFNRpc870HfMYoP4TtCmkMQINCW2vyO6uRLIRj14YGH+wxrcjIT2JSvJ50zM369Mo6M7aNFgl1iYGxvsmG7F3q28g0766Xky9+Twf4mtoG8iw/tIQ+Oz7wlKIzZUjgWzEKdg4QB/o75UjW3xOrwIGtKWQsOhN2+7cBnUkZnHGhZ3Bkrt97yWMa3yMdW9s9QU3PM+XHJ2xF7nC9zylqMR6wRU5K6Ex1r6h+h+M+Ia7aHhiUGOdOWnFGOECT1Sb5bvxS0XU3+qvTThFm8GVBnuF6lTi52jQDgeyMcI1NSoxGBPJpmdv1YTwmmg6to3muql/Cl4Tmj26YNutVl9DfwQ+WHg+I9dQPiHoa62Q6ZU07cj3RM7csR0i44WcZixgQDJUd9OxmhLPv/k2WUzPFJ6wF3xzzwtnH4Im1uY3cpptUGvOvJDjDOipqhCVAknOx1jGeWwHjhkBtqGlXRFzJmRgsA6vjClo3c0n6FM+f9znDlBHc/Q4P9nfiP+Lrj9cn3qy6jSDar2VFp0eBHkXQG0Vz2lKPiKN0WTOqxVIZNnASRFfOR3c6POyr7NdHlOGkWcI64K5A6P2xmP0SWhezbMsPGRowGmWzwsl4w/NINIYtWBzV6R8yFam0FWdGSOMT/AZx/vs3vxCaDJGhY6BeP2ROL580jBpDL77aRtfV8TWxRVB2oUSN6NsRBojIh0WSBztbVCS9H8b5c2XQ8Vl0qP93R3ye+/2zd4bP9op4i3VX9yViRhuXhs+s+3Dc3UElInKTxVHagEijTH79OJY8i0a/yNNkZXdKIKS/TsP4hg0Pna1PKvD+2+wVBQ4Vq/jM5f0BmunYtwxvw2CZlrTPhFpjPJT/Pd1sMRl96aq0KMTZwUzS7FhOlt955tEuETnVKuuaPv7MKZoA+L5+lnhYSs5Wj6IGc+muKVzpDEqsHx2RSqEZOVpeaNdP+NJqYR9UxdPNNtxVj4ZLpHodJrPeD2YASXbcu6rWHEGQ7ZASEQao0rPxheLOPobL8hztXLrZvxFvhNU+81SbuSYYK8XOunlDgMnOUcApmFD2ukPlsygbhBpDFG2OiINhjjdcjnP6dV/FZKg2XPcAwPBw8ZU/bXw7L0j7cFSzW+O0Jx8RBpDxGVHpGIRr1ow6FyzTD9FQaWQQeAWY5BTaBrFF2uRxujBKjDQJxSZyBlGx0kueOPH7xCvCjZ4AHDKD94WaM15hqRURi+kjtW+52MDrk2CEnHuL0Q/kAvNu8h3yUu0vHQXZojg5okIqE8xPmGJhjvcFxUOljhG2XewsB3IGWP9rz7yvZAlF2LGG+eHvjC61kXdeM678CvgGmsPxdrZ5M17hS7TMxFX1TGMJWeEGhSA5gauyIMh18klgmrAmSeo4hkDJV1XuGCm2WKSLfgRbAej7QrAw8HFEmp8KvOW/TGUFQzo3z2ui+Dyns7X4hA/HcUsEwZke0uDkIYg0Cphl8hqs4hsiYyZpg16U09TynAtwjWSZ7mFIHjkwhW+EJItjahiJ7xdBl/CxQRA/YMrkULI+DK3sQmSJxXFCescLsqwqt2AwTVmEeFvU9phB0H2JUME669N4QMPgqbq3tgsawhXQnkRuMJ1fMqAe7kVZ39O9B6zz/gERPZQOo1jAGuSwRrXkliv9tXq1RxgvbUJQuBhuBoC5p7zOWdi+RAHRwP6Xa6e8MPETEJQ9I6DSiJqslY8dJk+8bxFEwpzYmSTrXEnWG9tghB4HioXgd6t6wYn+SGubbpEOYPP2bt0oNi51l+zxEE5Y8Gl/xVM8Vy9GrQ3FbZPEhDK1NmSaPh3aRNEgVaq0AAC16VeuRIVOGh3SUf2N7g2gbxRMe6z8hpmAiXPhdf+rz+GgDbrPYHEnLMOcOaXC8VtNPr7+yu06kngd98NvILi8dQCN2nqbw1PGjbcdqpsINlO3A1owEausYUyG5RAzXUshFylER94cyCOS1Q0xfAerbob4I6IbWLL3+/Pe46nuZJtVsDTjHHQoIVe7cktEg8cwO2Apsl3aIgYrOmZTgjieWYiTtdx9zisp1Y5HQi3EwJG5jUe8XZlkkZzzvqMxkyCA2n5afuLnsIVVxrlxA94jY/+UmMkQRNMZ9wUqjzPvwTYQOuX20kmBI642yRy9+NTBrE61FcayIay+DbOdVAuEzlk6e/PFyO7CMAluaeT9ASD+n1fq5obECGyrOzrbk9Y2jgJhdy86A2NRZd7fxj04o8S8eLbTw8Wc7ZpQyZivvauGYFB8ymtYn5Ag0TM8Sgj5HJTCtcYlAn8saZ4+hHuA3O7IfUVs4gDZvvKRVpyH6yPVi1/IP5OfjIhqKfM30B2aImNZUuZFgZ34hGt+QFQZKXFd5jXm7sKMtzEi2GMMYuBlv/9WqXBAWmF+n0L8y8+MKd3d0gIY1VlzCrZgOXXqhQHSDPpqg3gWZFCbSSKRXwtVj35ay1RAsXhiDiQML06Jc5SUlJlrySW5CUmClM0nTaeKGNgOTN7ZCoGwHaRUwcG9EA99+x/LTmnMH3u/tHBoguYPnM7NVAsoEGcRzoJOlPlBs3Uw3fOQzjKRmViQcgTRmmNQLBcWsShBfKm6P4nvxhJULW2edEUr3r8D2TBl+9rxPAUuOgSnBvZNI5Ng5Zj+J0FoBC8UMJ5xYgNGsFSEcyb5Wp+fbj4GS0/7eNydytNqrm8r7n+x3LIhtyVy10Kmu/uWpSRAxRsD5Bb319kYFxgPiOvEVxAo9Cr+1wpeZGANCuYrmax4wF1eAcqwDX9pfgfXSxkgYZnPMYPvdG/hXSwodBgdHT0GRDVD2xAOi9aBMrZ6VE+YLqaPhW9zI+Okpg/jeXf6rjhABr/06DjQLehE+bif3THboSA5QI4zN0GYnlHzl17bwagQenVh/7h/gEquY+4oQDrAXoVdAZoD63qW8gGNpY2WgPI6W4yZwz0y64UzZK6t6yTC59oVcv7Q5fcc45Xd+MxXtXV3xeLGe4y8cwWD+jzXBn3WGmjzYtceBPv4rvPkh3wNVMe9TbXTpezptR08YKH+E55vmA9tb5n4OuOIU0OBdAYXPR9HfS4tFQB4BGNbUsr5d4E7uLxJBP3dmmPKnrbUmnlsDKn8puqBbEoAGPV3Xyi1/LyPVhhl2e0RMgGtgfbBR9Hrse6UgEVPxSU3HjKgq4NTbL12jDpVNmP5l43O2aoFW5ZCQxJTQStOGeP/aRXdwuY5qV7vI6WzH5fXWA7sb206d6c6O7u/jwW4vT+mfN00YW3j34WeMqemwJi6DDSGCFXIsPIHmWZV3nev3lL7z/fabaTBrYb6CXQ57VJd2ygTu9EZc7B/8y+HBVUj/GWqgVXfEsMM+RoUtEUqyOMZFry6zjvgq+KGXV/l39kKwdsRrvSOr/0WwLFBgr9fhQ+4dzdBe7FrIOgyFv64jdw/bMR5RdeR9v68r2pjh3j0HYuzQZaMdHZ2flxFDZiXZmGLTVviEtRNkhxLbffBKRT0PxffF2MoHIB2v0pUHG3m4sBFIpGDZSwMyuk+vu8RsgQ5aM/DKYogn1ywYSpitOVEKeuNLLCFdEkJx/iljsvQVh6z9nBSeE0sP2Bx0Af0K4ZPqA8O/X19WX1zEXDK57a8w08h66R/aUmGvfkfWTHnPbZHLLbV9V68SvBMqF363pvY8VkOXfKt1lu6wXDCIML47jzLzaJ/dj4H0aOgaYBDEKv9sOzFEbG30YBIkZycdBqmPeWiJQ+JI3o+xTkxZr1t5zgbSjHDFdC7Xp7U51cekVFGv2NBUbApaRjaQy4q1zI1ek4uGBD++fb2mWlBzLcBUvSjHIF/ZvRqwQbq1AHKfkQRwja9rKj0g41uMCj/NR4cqVA/QlvfqZnPQrINHmnhWWuKwge5W+b85z4jyy1TY8hmu/y5evv2q6lcAN9RnlkF+3C0gAZcOcx45i2dspDvhcMztmOChWTOMzOv/hrkQuWXeiB5N/03K1iekurER4S4wkXEYbJwJx+hLC0FMJnPsd8L+HwnX5vuZJYfMfp3tYlPLOSPiLxsgG6lGa8ko+YSJ8XytqX1LuAfmsC5WdQnguQNtXcF4FSt7+537DwSqw+crB7HiyxU3m0idZ3LtDWakvtNLkeMKcOwu8BU0j4bLIRwoN56Dho9TM3J4zZDai3aXziV5p+aVdklH/mXfQ1r6NlseaeBPqvH8RDuMWRRZDYu0EZnYuvm/aEzPOlnnOlQ0Z/xFunbvbiYCfRI9OM4xnWwaToJMokHA146rr2hqPkwPLqp26U6WTtlEe81tce9Jon3y7ucnjygSeuOBJSFe6uH9I8Yhcw60HCkC50rVvlVf/qB6VvHzA4R5HmF+8SbzZpYH+yX7WLCwMS2A2UugfCncnlD10mam1XYYtKx+4pt6nGvfESHCnoO1He/MR0hs7jSIO49OZb6IYYZQr6gVsK5hMHdI6RgIL33PP+XcK50Pz8JA1b2qmGLwAPeKUYWwu0XzMf60sDIr4LCXBydaKvY5tXPeFQb9oQTCPsCHGk51h5bKx8wfcLJ8tkKw6YqXz0ft766U8EDoAzA2kz/RxXNzy6SBtnud06Nm2RScV9m2M7n2e7ppeYOUiUx3iW1HklsUL7130aOA2MgIgzJAUH6K6NRwmzzuVFIM6llMjj9x2zExsfu8Z/i61yiNIIwuO6aanm37LNzxUKr/GiDoRnVhmPw778F0EVcziEXd7ESXPotGG6a81yudMkvkLhaLrg8m96nY475H3fGUOg80G78Jw8NzDToP2cG4Mg4NsR4WmJ6QAbln6B/co5ClREYmfNv+Q/vR4Iu3HQB7rM4RZjsEMWjT804mjNgKMH5YrZZ3wKMgI9TebeMYZxuPWepltofv4232G01S4c3nmffmfsilYyGp1ETj2sdI6ZDHEUo3lDR0vocS8O7W/3tfM2EPA6kHNbnR6nZAUQe0tKQuh0TlltmDbi4JUFct7Y6gh2IA86uA4y0CMNbydimEgeJOTD0UmcN1Bm4ajBz86lOFcpGMkmjhY5J461rz/qbw0cEzIencAsvmOsjLY2KPvUXPeTIZHXWB8u+dNcq7G/2e/KAm4gwI9ASYkPoDkcXZ3mcky4GERjmZrfHJkYBbo3NIkK2beN8MPy7ag49wvOnUyaBdqji5B8H+UtuessP05CNhgQJmt6biKmnQ8mGQXyDAXPvo6kwEcvAKEXVZ/oSMvlR4KO/ji6lHx6AbHfuDqzPULbYL+z/5UVosAP+4FST980PzdJ3rBEQ5eIeKht9d9u1NxDbKqeIowTKccJ75eOjIMaS8ofdueyDhTUuh2ygAuU+Ov5UhwZ82MOgY8jVRw0QSg/9WOR8pGRedU6f7PB6055n4JzRCs6YSQ+fBeZjtPkJ/Y/+UBZIgQejtMwCfDUIe8vKfU6PSB2JojeI6MYkGuIph5hz9VlIlR2NNVpmBD0h26f//eXuWXeRuuGn1zAc3e0Rgu84DItjlbjPiuGy3HQrXiEIRGWG3bGI78BHTZUQ5gVZtewJSWMeLwUkLcHpIF8oCzhAw8+CYq6llRwyUprbV+Ic2RYCkLD8oaAuLKLHM9rGs0tAn5YMMcpH/K6N6/VUCGqrqR8ZL2VSJeMlKlxXKDzhgX0emHJWuxwCrfxG84Iua8ixhxUuiWmyLYW8dIxZMwBYl68zMylLyLIB+QHYQx8fzso4lTTxtYlcyAYslGGZjoRQoezEq2v3q+lCMF7N6LuV8vERyWnmziW3X9hEMaEZ7ocUdIaJw4yZONjvxRj5jBPdDimAt4v2LW2UUOGqLrqe1IHO+zCaw5O5MnLDqqu/E5EfhoKSpOBDMAcp+Pf28kcZfji1PtSKFty9zhfkndkUkoiAyx/8JLEqkBuuhyDOR1vYxAeHdFw+2kaIgT3e3hUwX6LSdwp5gYcd2LNHUVJDIg967yLDnTIBGBIjGw8Ch4HNwFpnBNhSDDWqievQ15RZRxXUbROF0VaJP3SEsWDRZCX4jKQgfJDGacUt4sToGfTWgzZ+yDBIRw1lERwvPCrooCyQTX28gcuDu5xEkLnU0tK36VxdK1r9N3qxRkEDcT9k0zgPovrrSazrPpbcheUjMwLPW0Vvpl+tjcu1FA+ZER6/BrUY6dI2kNFLFeWTbovkDmiN9lZEE/9QyWEOog6Am6KxUcPMsz8Sw+KLmdRTvotsa9eMOB5F1Gx61DPkbD+tlOdOhEb7MCVT16PqShcvpNpeTdXHLyDrwZLxcgoo9Njy8v3srU1pA8exOIpO1tgHkoiczRbt0HFQb4gc7gNd1CZpb//uSTiSnxICI3Lt5BLvji4A+o7y7FXEL7HweYX70Txfd0FNaNcERiNLg1kOP/HhUMO+dRk0tOpjb72zbIMta/koibU1np2NC+WjT1eZ2XCcLQl8y5/mNZ6UTAPCoWRm3GHmtCuNTccjYq7j3OSL8gcyTUggUgLr/6fxHA89ET5YD+n+perCF9PEH37+LZyX4R3PS+7/3xJg8+52uD1X/alTQY0O5h6GEaqZ25OjFTdG5q9aireDIOgTehem5c20K50muRvTb34feZxozD1RH16CjDCDJccFyG8eDwTnEHnUUfmcKrMGMkX5oZe3kgSGGTMR50jCDty2R8uiiwzDbEDzMjH/+Wnf1wUY3FsW1whVw6xU5mO2J7GsH35ApGBgredUwZV3/H2QRq8l2V7Y7XGDDGAv9rfYuoZztE4IF8/ZN8HYYN8QeZw7lGTObhPEHkjhpPQCfQOvtohCBLdG1aLt3CZZhwdxunGpUXlioYKPoYJ44xymh62zX1BhEunHIZpi/oW+qh3oaN1uTePo9yIYAyfqALoceiHCPIFmcNp7CDMMfYTyYYeZuKUwc2/tF1GWrw33H66Hz4oe5m35J6kApguFLhrGmUMPx69lHVjtRZH68v3SaOaOKLvQKdzG94Frq5WPn6tLH2HYic7d8LLhpEj7WQd+YLMkdwPB8gcco/yCGMOQxT2uIVOT/I2ePCH/jSM0ooCbfX4Q/FLcoDkKoJvfPJt9lcZ9BrtQnBRNohTF1XjcV/ABJV4nMqGXb5wEl6A0R+RF8QF8gWZw32XMQRSStSJt2pEEbfbdxPzQXNdAO0k2LHyOxi78vwvZjTd50uwftbTcks6Bc6ZJ37QW/30zZiLM5v7N0waHTAVNY70zdG3fbMcZeDlONzwSyrPRhChbRZcdhDqn7pamU7muF2/J0AP+iNpjsxEMmQf/z5x1SDPUHleqeC6sSQNnALQKvotM7j1LUvkQP7w1fgy0o7Q0dYmthdVFWn1JV+QOY7U7wlsbZjtC3iOxEc0SQft5W2yfPEb8A4wrjqanrnF69maeX+FOhIKmJUXfMlrdgiz1L5yf2U4FYWFEsu8wbpfNQ7yBZmDLhqdUgmHSZrpuSytRy6ZTbsHUMHokNnR1CCMwetHadbHHedsoPXU4ttOw6iwj3MZzPvd6PJpRxlhSdxbYpl7trlPs5IfyBfceNsVH9znUjDktLx0N+biobH+KgZxk2vFI1eIxtMGzfK4+bXZsXtrQMk9Tdagqn3TwinO3dy1U//kzTr5Q5E9lZFMFOZpnJ1mla/8sKts2+PLgSCnZMJLgGmLsCMMnWTiupuOyWlEMPAF0r/KPeOsI3dIKUt0NDnNW1Kx8skJO4a8AQbmAbT21SmKcf9U3IHCGASe7YUHD/s/J9H69/tiNhQjj7hq4RTYtT43AZR7K8sevCSV6TlNcMXB02Npdpdx0MbWn4JHLoPQ4p0LjQyjBvlgL2UNH3j4HZBT9uBQWnvTsVELrBFE7Eiez9i+YoGWOB1baqeLgY2o23N4y5k2GYi3Z25blvnANnUGC6/+7xE7vXA6Yfk61yTvkiPY/+QDZYkQ+I3+w8Ob8mOgqwI6cx15qxcsIU/8gNdWkRQWbfBKNW7by61jhQz96HBu3S+6/vBUlTPR19XuzT33CyNueiGTl5++v7chQzuh/yfgn9tvO37YB/Q6A7ogGr8hcq+QK1FG4MHnfEAbUl4DMxuNxfW+KKuszvRHi91lbq765fe99TOfSh2GXaCcRgHVLuewEut2/Hu9VX+9QUvoBPt9H2UFN8A9XwKl+uBY9cQEX9E0At4MLlmX//EXiZUJDxBx+uhan909ErGldob4BaVNBo9e+ruy2ZVhNGpum/t8wlqN2Fz9urbRcLeTX4Ylvz9PS5YE+nsV+11ZIDMQkB6GU/XHjX+mzwlXQYaOOH/SL3ncOIeMsuyBi2RvRewvwMi802jVX37tdTqMgfNBL2QvjlLcq+FhJ6ZPOWzBZd/02ptitxD193trXnsoVOUPE1GnQfuRNLCf2d/a9bkBEY4FpTp5aHnhThl+XQUqNZEx5rNDHDaQopexzPrk7cU0KNPEaR+Ty/AcNwxnBJe7GytfkoPRMqVSVW+NnMyv9oajE7II49FOdbhWeuyfFY9epaVJgv3LftYuzw+IeBjIvTMDtFVO9gsyhFMMO5mHiTYtSopGm6v+4c3AyoJvK/c5qA1d9eT1so2fDZQpOA3FR6I0tM1/WbwTzh77KekEGgrL3b8xu1Qq1cRp3pAqEulJaA/fuj6l+7Rff6JdXRiQCP2ApXoB4VY1vQ8PiQMXStyn7Ivh+o+aewgKgXRnyWE/vo3vAi3IuJO7/OHLxRaV1uxcsk5FJ9I7DtNa98bjCVvTNFDGWXrfuU4PhjzpJlbpR5V+655LdF4LQrV+GtCfW9mv2sWDA9L7MhJLvdqPSiIa2DitsIpFSJcGM9mOE2QCvQFza51W3xxdsi7LdflKZllwxbcht/ymYM9ANE6SI6UlszYvExPGGsphKQY8hPbjl7VriwMk+F4k/KLkkAL/kE6JNqHAHPW3nKg5ZQedvZAZaCQt+x5Oo548CWXg6EKHMVS6caXU2bJUZItcsLl2mthtFv0FAhNz2Z08WxwF+o+HhN+rXVp8IIMrQKlyCEFjF2mAYjUC0qFtRiY1Ns+mrJ/xF19DSb1FKRg0hWjpRfvRJfeeI8N5JrllKZaUxdQTkVnrJ56SUQ/D/gJdrl1YWiC/TyCz6BGuGGh/yaVmZOUwKCqTJSl9alGdz5Pvq5++RexJKe9wBeOON8TEqQjLW2pt6VZq7esPy2k3nsanLsUPE4tTAHEJXX3NIWJSkAnsJ9D+2nVDB2Q6GuT0AmRAQZFnPmQvQxQy7srmShwVKC+IXWaxh+cSEKczOSJRJHmMLwGFW74cmYB+4VByinbV8GDt2rW8h/pOUMaphstJntmQShZxWP2nIJmi98JIcXDqppkB+wF0B2g37aLhBwrzYVCqozkDCm90xUTBdUiWvzswcZTgZuGKhy7Leq6XQPv/jf2gXTLygMLtC3pUy5sRHBobJp4sb0WaXcU/G7EdOAXV/PoI5wk/F9Dej4A+pF0w8oEy7w66BoXOON0QHE1oi1F749H+cnMHkCWKSqgvmaL62oMhuM6UZXg2sF1BVPrs2HfRohI/BOVsd0eNIh238Oihabg3D8NoffAS8FKfxfeM8zpztF4j2I6gN99VoqgUfalPAOWmk1bwiMSyBy4UX5q0suIyriSKthKQrFawnOcqi7dc81hmJiNnF9he2m4jR8gsJVBRura8CpR9VywGjiwbK18Uzzi81YBvIq3OS6q+z0YywmGZTcblrvEl3/BWPHyZ1zbnWbn0J1+wXUBXsp20yf45gQbYE/RjUHYj0BTQjoMelqnO3jD7WbnNgdrSWcajT5GJZ194CJtXePA2Bjp/o3Iu7expLmD9tR321KZ5C3GgnWgNfzGI2r3Mh1YHCQrDPHxNZ7RybgX/eVVI3IFLscF6gapAvOToLWYoFGhL3hR1MGgSqByUfdE/gsDyarkngg6eMmXKzlq1t1AKoM13Ah2Axj6kr6+Pm4GTQVEXwUMM5q/luBx0CB4dABqe6zvfghvokJ3ROVwZjQJRKXcQaAxoPOgB0MugStAKUFt/fz9HIprM0Uqnj9/5XH9nOIZnPMZnOkyP6TL93ZifZv0mwtve9n/7youCiMW9VwAAAABJRU5ErkJggg==",
                new kakao.maps.Size(32, 32),
                { offset: new kakao.maps.Point(18, 18) })
        })));
    }

    setOverlay(callback) {
        const markers = this.clusterer._markers;
        markers.forEach(marker => {
            const position = marker.getPosition();
            const [latitude, longitude] = [position.Ma, position.La];
            let content = document.createElement("div");
            content.innerHTML = typeof callback === "function" ? callback(latitude, longitude) : callback.toString();
            content = content.querySelector("div.overlay");

            const overlay = new kakao.maps.CustomOverlay({
                map: this.map,
                content,
                position: new kakao.maps.LatLng(latitude, longitude),
                zIndex: 999
            });
            overlay.setVisible(false);

            const parent = overlay.getContent().parentElement;
            parent.classList.add("overlay-parent");

            kakao.maps.event.addListener(marker, "mouseover", () => overlay.setVisible(true));
            kakao.maps.event.addListener(marker, "mouseout", () => overlay.setVisible(false));
            content.parentElement.onmouseleave = () => overlay.setVisible(false);
        });
    }

    relayout() {
        this.map.relayout();
    }

    addMarkerEventListener(eventListener, callback) {
        this.clusterer._markers.forEach(marker => kakao.maps.event.addListener(marker, eventListener, () => {
            const position = marker.getPosition();
            callback(position.Ma, position.La);
        }));
    }
}
