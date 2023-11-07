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

const Card = {
  baseStyle: {
    p: "22px",
    display: "flex",
    flexDirection: "column",
    width: "100%",
    boxShadow: "0px 5px 14px rgba(0, 0, 0, 0.05)",
    borderRadius: "20px",
    position: "relative",
    wordWrap: "break-word",
    backgroundClip: "border-box",
  },
  variants: {
    panel: (props) => ({
      bg: props.colorMode === "dark" ? "#111C44" : "white",
    }),
  },
  defaultProps: {
    variant: "panel",
  },
};

export const CardComponent = {
  components: {
    Card,
  },
};
