import React from 'react';
import { Link } from '@cloudscape-design/components';
import { addColumnSortLabels } from './labels';

export const DEFAULT_PREFERENCES = {
  pageSize: 30,
  visibleContent: ['cuisine', 'deliveryMethod'],
  wrapLines: false,
};

export const COLUMN_DEFINITIONS = addColumnSortLabels([
  {
    id: 'cuisine',
    sortingField: 'cuisine',
    header: 'Cuisine',
    cell: item => (
      <div>
        <Link href="#">{item.name}</Link>
      </div>
    ),
    minWidth: 180,
  },
  {
    id: 'deliveryMethod',
    sortingField: 'deliveryMethod',
    header: 'Delivery method',
    cell: item => item.deliveryMethod,
    minWidth: 100,
  },
  {
    id: 'priceClass',
    sortingField: 'priceClass',
    header: 'Price class',
    cell: item => item.priceClass,
    minWidth: 100,
  }
]);

export const PAGE_SIZE_OPTIONS = [
  { value: 10, label: '10 Distributions' },
  { value: 30, label: '30 Distributions' },
  { value: 50, label: '50 Distributions' },
];

export const FILTERING_PROPERTIES = [
  {
    propertyLabel: 'Cuisine name',
    key: 'cuisine',
    groupValuesLabel: 'Cuisine name values',
    operators: [':', '!:', '=', '!='],
  },
  {
    propertyLabel: 'Delivery method',
    key: 'deliveryMethod',
    groupValuesLabel: 'Delivery method values',
    operators: [':', '!:', '=', '!='],
  },
  {
    propertyLabel: 'Price class',
    key: 'priceClass',
    groupValuesLabel: 'Price class values',
    operators: [':', '!:', '=', '!='],
  }
];

export const PROPERTY_FILTERING_I18N_CONSTANTS = {
  filteringAriaLabel: 'your choice',
  dismissAriaLabel: 'Dismiss',

  filteringPlaceholder: 'Filter distributions by text, property or value',
  groupValuesText: 'Values',
  groupPropertiesText: 'Properties',
  operatorsText: 'Operators',

  operationAndText: 'and',
  operationOrText: 'or',

  operatorLessText: 'Less than',
  operatorLessOrEqualText: 'Less than or equal',
  operatorGreaterText: 'Greater than',
  operatorGreaterOrEqualText: 'Greater than or equal',
  operatorContainsText: 'Contains',
  operatorDoesNotContainText: 'Does not contain',
  operatorEqualsText: 'Equals',
  operatorDoesNotEqualText: 'Does not equal',

  editTokenHeader: 'Edit filter',
  propertyText: 'Property',
  operatorText: 'Operator',
  valueText: 'Value',
  cancelActionText: 'Cancel',
  applyActionText: 'Apply',
  allPropertiesLabel: 'All properties',

  tokenLimitShowMore: 'Show more',
  tokenLimitShowFewer: 'Show fewer',
  clearFiltersText: 'Clear filters',
  removeTokenButtonAriaLabel: () => 'Remove token',
  enteredTextLabel: text => `Use: "${text}"`,
};