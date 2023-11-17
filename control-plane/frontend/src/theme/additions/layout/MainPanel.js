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

const MainPanel = {
  baseStyle: {
    float: "right",
    maxWidth: "100%",
    minHeight: "100vh",
    height: "100%",
    overflow: "auto",
    position: "relative",
    maxHeight: "100%",
    transition: "all 0.33s cubic-bezier(0.685, 0.0473, 0.346, 1)",
    transitionDuration: ".2s, .2s, .35s",
    transitionProperty: "top, bottom, width",
    transitionTimingFunction: "linear, linear, ease",
  },
  variants: {
    main: (props) => ({
      float: "right",
    }),
    rtl: (props) => ({
      float: "left",
    }),
  },
  defaultProps: {
    variant: "main",
  },
};

export const MainPanelComponent = {
  components: {
    MainPanel,
  },
};
