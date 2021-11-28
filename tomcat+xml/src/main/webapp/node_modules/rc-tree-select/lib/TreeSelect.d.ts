import { TreeSelectProps } from './generate';
declare const TreeSelect: (<ValueType = import("./interface").DefaultValueType>(props: TreeSelectProps<ValueType> & {
    children?: import("react").ReactNode;
} & {
    ref?: import("react").Ref<import("rc-select/lib/generate").RefSelectProps>;
}) => import("react").ReactElement<any, string | import("react").JSXElementConstructor<any>>) & {
    TreeNode: import("react").FC<import("./TreeNode").TreeNodeProps>;
    SHOW_ALL: "SHOW_ALL";
    SHOW_PARENT: "SHOW_PARENT";
    SHOW_CHILD: "SHOW_CHILD";
};
export { TreeSelectProps };
export default TreeSelect;
