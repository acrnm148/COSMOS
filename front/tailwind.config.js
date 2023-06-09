/** @type {import('tailwindcss').Config} */

module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  darkMode: "class",
  theme: {
    extend: {
      colors: {
        darkBackground: "#262D33",
        darkBackground2: "#585858",
        darkMain: "#BE6DB7",
        darkMain2: "#9C4395",
        darkMain3: "#973F90",
        darkMain4: "#782472",
        darkMain5: "#F9F0F8",
        darkMain6: "#E4C3E1",
        lightMain: "#FF8E9E",
        lightMain2: "#FFB5C0",
        lightMain3: "#FFDBE0",
        lightMain4: "#FFF0F2",
        calendarGray: "#F4F4F4",
        calendarDark: "#B9B9B9",
        serveyCircle: "#D9D9D9",
      },
      fontFamily: {
        baloo: ["Baloo 2", "cursive"],
      },
    },
  },
  variants: {
    // Add variants as needed
    scrollSnapType: ["responsive"],
  },

  plugins: [require("tailwind-scrollbar-hide")],
};
