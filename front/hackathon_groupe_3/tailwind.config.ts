import type { Config } from "tailwindcss";

export default <Partial<Config>>{
    theme: {
        extend: {
            colors: {
                primary: "green",
                secondary: "blue",
                danger: "red",
            },
        },
    },
};
