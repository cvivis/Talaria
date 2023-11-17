/*!

=========================================================
* Argon Dashboard Chakra PRO - v1.0.0
=========================================================

* Product Page: https://www.creative-tim.com/product/argon-dashboard-chakra-pro
* Copyright 2022 Creative Tim (https://www.creative-tim.com/)

* Designed and Coded by Simmmple & Creative Tim

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/

import { mode } from "@chakra-ui/theme-tools";
export const buttonStyles = {
  components: {
    Button: {
      variants: {
        primary: {
          fontSize: "10px",
          bg: "blue.400",
          color: "#fff",
          _hover: { bg: "blue.300" },
          _focus: { bg: "blue.300" },
          _active: { bg: "blue.300" },
        },
        navy: {
          fontSize: "10px",
          bg: "navy.900",
          color: "#fff",
          _hover: { bg: "navy.800" },
          _focus: { bg: "navy.800" },
          _active: { bg: "navy.800" },
        },
        "no-effects": {
          _hover: {},
          _active: {},
          _focus: {},
        },
        danger: () => ({
          color: "white",
          bg: "red.500",
          fontSize: "10px",
          _hover: "red.400",
          _focus: "red.400",
          _active: "red.400",
        }),
        outlined: (props) => ({
          color: mode("blue.400", "white")(props),
          bg: "transparent",
          fontSize: "10px",
          border: "1px solid",
          borderColor: { bg: mode("blue.400", "white")(props) },
          _hover: { bg: mode("blue.50", "transparent")(props) },
          _focus: { bg: mode("blue.50", "transparent")(props) },
          _active: { bg: mode("blue.50", "transparent")(props) },
        }),
        dark: (props) => ({
          color: "white",
          bg: mode("gray.700", "blue.500")(props),
          fontSize: "10px",
          _hover: { bg: mode("gray.700", "blue.500")(props) },
          _focus: { bg: mode("gray.700", "blue.600")(props) },
          _active: { bg: mode("gray.700", "blue.400")(props) },
        }),
        light: (props) => ({
          color: mode("gray.700", "gray.700")(props),
          bg: mode("gray.100", "white")(props),
          fontSize: "10px",
          _hover: { bg: mode("gray.50", "white")(props) },
          _focus: { bg: mode("gray.50", "white")(props) },
          _active: { bg: mode("gray.50", "white")(props) },
        }),
      },
      baseStyle: {
        fontWeight: "bold",
        borderRadius: "8px",
        fontSize: "10px",
      },
    },
  },
};
