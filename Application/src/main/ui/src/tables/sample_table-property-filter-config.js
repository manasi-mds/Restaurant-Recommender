import React from 'react';
import { Link } from '@cloudscape-design/components';
import { addColumnSortLabels } from '../commons/labels';

export const DEFAULT_PREFERENCES = {
  pageSize: 30,
  visibleContent: ['cuisine', 'deliveryMethod'],
  wrapLines: false,
};

export const COLUMN_DEFINITIONS = addColumnSortLabels([
  {
    id: 'cuisine',
    sortingField: 'name',
    header: 'Cuisine',
    cell: item => item.name,
    minWidth: 180,
  },
  {
    id: 'deliveryMethod',
    sortingField: 'deliveryMethod',
    header: 'Delivery method',
    cell: item => item.id,
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
    key: 'name',
    groupValuesLabel: 'Cuisine name values',
    operators: [':', '!:', '=', '!='],
  },
  {
    propertyLabel: 'Distribution ID',
    key: 'id',
    groupValuesLabel: 'id values',
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

