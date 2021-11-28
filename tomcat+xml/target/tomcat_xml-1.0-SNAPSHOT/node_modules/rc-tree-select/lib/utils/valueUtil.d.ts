/// <reference types="react" />
import type { FilterFunc } from 'rc-select/lib/interface/generator';
import type { FlattenDataNode, RawValueType, DataNode, DefaultValueType, LabelValueType, LegacyDataNode, FieldNames } from '../interface';
import type { SkipType } from '../hooks/useKeyValueMapping';
declare type CompatibleDataNode = Omit<FlattenDataNode, 'level'>;
export declare function toArray<T>(value: T | T[]): T[];
/**
 * Fill `fieldNames` with default field names.
 *
 * @param fieldNames passed props
 * @param skipTitle Skip if no need fill `title`. This is useful since we have 2 name as same title level
 * @returns
 */
export declare function fillFieldNames(fieldNames?: FieldNames, skipTitle?: boolean): FieldNames;
export declare function findValueOption(values: RawValueType[], options: CompatibleDataNode[]): DataNode[];
export declare function isValueDisabled(value: RawValueType, options: CompatibleDataNode[]): boolean;
export declare function isCheckDisabled(node: DataNode): boolean;
/**
 * Before reuse `rc-tree` logic, we need to add key since TreeSelect use `value` instead of `key`.
 */
export declare function flattenOptions(options: any): FlattenDataNode[];
/** Filter options and return a new options by the search text */
export declare function filterOptions(searchValue: string, options: DataNode[], { optionFilterProp, filterOption, }: {
    optionFilterProp: string;
    filterOption: boolean | FilterFunc<LegacyDataNode>;
}): DataNode[];
export declare function getRawValueLabeled(values: RawValueType[], prevValue: DefaultValueType, getEntityByValue: (value: RawValueType, skipType?: SkipType, ignoreDisabledCheck?: boolean) => FlattenDataNode, getLabelProp: (entity: FlattenDataNode, val: RawValueType) => React.ReactNode): LabelValueType[];
export declare function addValue(rawValues: RawValueType[], value: RawValueType): RawValueType[];
export declare function removeValue(rawValues: RawValueType[], value: RawValueType): RawValueType[];
export {};
