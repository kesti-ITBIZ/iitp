import store from "../../store";

export const alert = (title, onOk = () => null) => store.dispatch("SET_ALERT_VISIBLE", { show: true, title, okButtonText: "확인", onOk });

export const sleep = async milliseconds => await new Promise(resolve => setTimeout(resolve, milliseconds));

export const execAsync = async (...api) => {
    let count = 0, results = {};
    for (let i = 0; i < api.length; ++i)
        (async () => {
            try {
                results[i] = await api[i]();
            } catch (e) {e;} finally {
                ++count;
            }
        })();
    while (count !== api.length)
        await sleep(10);
    return results;
};
