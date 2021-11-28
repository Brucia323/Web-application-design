import * as React from 'react';
import type { SelectProps, RefSelectProps, GenerateConfig } from 'rc-select/lib/generate';
import type { IconType } from 'rc-tree/lib/interface';
import type { FilterFunc } from 'rc-select/lib/interface/generator';
import TreeNode from './TreeNode';
import type { Key, DefaultValueType, DataNode, LabelValueType, SimpleModeConfig, RawValueType, ChangeEventExtra, LegacyDataNode, FlattenDataNode, FieldNames } from './interface';
import type { CheckedStrategy } from './utils/strategyUtil';
import { SHOW_ALL, SHOW_PARENT, SHOW_CHILD } from './utils/strategyUtil';
export interface TreeSelectProps<ValueType = DefaultValueType> extends Omit<SelectProps<DataNode[], ValueType>, 'onChange' | 'mode' | 'menuItemSelectedIcon' | 'dropdownAlign' | 'backfill' | 'getInputElement' | 'optionLabelProp' | 'tokenSeparators' | 'filterOption' | 'fieldNames'> {
    multiple?: boolean;
    showArrow?: boolean;
    showSearch?: boolean;
    open?: boolean;
    defaultOpen?: boolean;
    value?: ValueType;
    defaultValue?: ValueType;
    disabled?: boolean;
    placeholder?: React.ReactNode;
    /** @deprecated Use `searchValue` instead */
    inputValue?: string;
    searchValue?: string;
    autoClearSearchValue?: boolean;
    maxTagPlaceholder?: (omittedValues: LabelValueType[]) => React.ReactNode;
    fieldNames?: FieldNames;
    loadData?: (dataNode: LegacyDataNode) => Promise<unknown>;
    treeNodeFilterProp?: string;
    treeNodeLabelProp?: string;
    treeDataSimpleMode?: boolean | SimpleModeConfig;
    treeExpandedKeys?: Key[];
    treeDefaultExpandedKeys?: Key[];
    treeLoadedKeys?: Key[];
    treeCheckable?: boolean | React.ReactNode;
    treeCheckStrictly?: boolean;
    showCheckedStrategy?: CheckedStrategy;
    treeDefaultExpandAll?: boolean;
    treeData?: DataNode[];
    treeLine?: boolean;
    treeIcon?: IconType;
    showTreeIcon?: boolean;
    switcherIcon?: IconType;
    treeMotion?: any;
    children?: React.ReactNode;
    filterTreeNode?: boolean | FilterFunc<LegacyDataNode>;
    dropdownPopupAlign?: any;
    onSearch?: (value: string) => void;
    onChange?: (value: ValueType, labelList: React.ReactNode[], extra: ChangeEventExtra) => void;
    onTreeExpand?: (expandedKeys: Key[]) => void;
    onTreeLoad?: (loadedKeys: Key[]) => void;
    onDropdownVisibleChange?: (open: boolean) => void;
    /** `searchPlaceholder` has been removed since search box has been merged into input box */
    searchPlaceholder?: React.ReactNode;
    /** @private This is not standard API since we only used in `rc-cascader`. Do not use in your production */
    labelRender?: (entity: FlattenDataNode, value: RawValueType) => React.ReactNode;
}
export default function generate(config: {
    prefixCls: string;
    optionList: GenerateConfig<any>['components']['optionList'];
}): (<ValueType = DefaultValueType>(props: TreeSelectProps<ValueType> & {
    children?: React.ReactNode;
} & {
    ref?: React.Ref<RefSelectProps>;
}) => React.ReactElement) & {
    TreeNode: typeof TreeNode;
    SHOW_ALL: typeof SHOW_ALL;
    SHOW_PARENT: typeof SHOW_PARENT;
    SHOW_CHILD: typeof SHOW_CHILD;
};
