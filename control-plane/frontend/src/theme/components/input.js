import { mode } from "@chakra-ui/theme-tools";
export const inputStyles = {
  components: {
    Input: {
      baseStyle: {
        field: {
          fontWeight: 400,
          borderRadius: "8px",
        },
      },

      variants: {
        main: (props) => ({
          field: {
            bg: mode("white", "navy.900")(props),
            border: "1px solid",
            borderColor: mode("gray.200", "transparent")(props),
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
        auth: (props) => ({
          field: {
            bg: mode("white", "navy.700")(props),
            border: "1px solid",
            borderColor: mode("gray.200", "transparent")(props),
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
        authSecondary: (props) => ({
          field: {
            bg: mode("white", "navy.800")(props),
            border: "1px solid",
            borderColor: mode("gray.200", "transparent")(props),
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
        search: (props) => ({
          field: {
            border: "none",
            py: "11px",
            borderRadius: "inherit",
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
      },
    },
    NumberInput: {
      baseStyle: {
        field: {
          fontWeight: 400,
        },
      },

      variants: {
        main: (props) => ({
          field: {
            bg: mode("white", "navy.900")(props),
            border: "1px solid",
            borderColor: mode("gray.200", "transparent")(props),
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
        auth: (props) => ({
          field: {
            bg: mode("white", "navy.700")(props),
            border: "1px solid",
            borderColor: mode("gray.200", "transparent")(props),
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
        authSecondary: (props) => ({
          field: {
            bg: mode("white", "navy.800")(props),
            border: "1px solid",
            borderColor: mode("gray.200", "transparent")(props),
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
        search: (props) => ({
          field: {
            border: "none",
            py: "11px",
            borderRadius: "inherit",
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
      },
    },
    Select: {
      baseStyle: {
        field: {
          fontWeight: 400,
        },
      },

      variants: {
        main: (props) => ({
          field: {
            bg: mode("white", "navy.900")(props),
            border: "1px solid",
            borderColor: mode("gray.200", "transparent")(props),
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
        auth: (props) => ({
          field: {
            bg: mode("white", "navy.700")(props),
            border: "1px solid",
            borderColor: mode("gray.200", "transparent")(props),
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
        authSecondary: (props) => ({
          field: {
            bg: mode("white", "navy.800")(props),
            border: "1px solid",
            borderColor: mode("gray.200", "transparent")(props),
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
        search: (props) => ({
          field: {
            border: "none",
            py: "11px",
            borderRadius: "inherit",
            _placeholder: { color: mode("gray.300", "gray.400")(props) },
          },
        }),
      },
    },
  },
};
