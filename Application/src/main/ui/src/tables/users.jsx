import React from 'react';
import { useCollection } from '@cloudscape-design/collection-hooks';
import PropertyFilter from '@cloudscape-design/components/property-filter';
import Pagination from '@cloudscape-design/components/pagination';
import { Button, SpaceBetween, CollectionPreferences, Box, Header, Link } from '@cloudscape-design/components';
import Table from '@cloudscape-design/components/table';

import { paginationLabels, distributionSelectionLabels } from '../commons/labels';
import { PROPERTY_FILTERING_I18N_CONSTANTS } from '../commons/operators.js';

import '../base.scss';

const getFilterCounterText = count => `${count} ${count === 1 ? 'match' : 'matches'}`;

const getHeaderCounterText = (items = [], selectedItems = []) => {
    return selectedItems && selectedItems.length > 0 ? `(${selectedItems.length}/${items.length})` : `(${items.length})`;
  };

  const getServerHeaderCounterText = (totalCount, selectedItems) => {
    return selectedItems && selectedItems.length > 0 ? `(${selectedItems.length}/${totalCount}+)` : `(${totalCount}+)`;
  };

function getCounter(props) {
    if (props.counter) {
      return props.counter;
    }
    if (!props.totalItems) {
      return null;
    }
    if (props.serverSide) {
      return getServerHeaderCounterText(props.totalItems, props.selectedItems);
    }
    return getHeaderCounterText(props.totalItems, props.selectedItems);
  }

  const InfoLink = ({ id, onFollow, ariaLabel }) => (
    <Link variant="info" id={id} onFollow={onFollow} ariaLabel={ariaLabel}>
      Info
    </Link>
  );
const TableHeader = props => {
    return (
      <Header
        variant={props.variant}
        counter={getCounter(props)}
        info={
          props.loadHelpPanelContent && <InfoLink onFollow={props.loadHelpPanelContent} ariaLabel={`Information about ${props.title}.`} />
        }
        description={props.description}
        actions={props.actionButtons}
      >
        {props.title}
      </Header>
    );
  };

const FullPageHeader = ({
    resourceName = 'Users',
    ...props
  }) => {
    const isOnlyOneSelected = props.selectedItems.length === 1;
    return (
      <TableHeader
        variant="awsui-h1-sticky"
        title={resourceName}
        actionButtons={
          <SpaceBetween size="xs" direction="horizontal">
            <Button disabled={!isOnlyOneSelected}>View details</Button>
            <Button disabled={props.selectedItems.length === 0}>Use this for likes</Button>
            <Button disabled={props.selectedItems.length === 0}>Use this for dislikes</Button>
          </SpaceBetween>
        }
        {...props}
      />
    );
  };

const TableNoMatchState = props => (
    <Box margin={{ vertical: 'xs' }} textAlign="center" color="inherit">
      <SpaceBetween size="xxs">
        <div>
          <b>No matches</b>
          <Box variant="p" color="inherit">
            We can't find a match.
          </Box>
        </div>
        <Button onClick={props.onClearFilter}>Clear filter</Button>
      </SpaceBetween>
    </Box>
  );
  
 const TableEmptyState = ({ resourceName }) => (
    <Box margin={{ vertical: 'xs' }} textAlign="center" color="inherit">
      <SpaceBetween size="xxs">
        <div>
          <b>No {resourceName.toLowerCase()}s</b>
          <Box variant="p" color="inherit">
            No {resourceName.toLowerCase()}s associated with this resource.
          </Box>
        </div>
        <Button>Create {resourceName.toLowerCase()}</Button>
      </SpaceBetween>
    </Box>
  );
  
  export const PAGE_SIZE_OPTIONS = [
    { value: 10, label: '10 users' },
    { value: 30, label: '30 users' },
    { value: 50, label: '50 users' },
  ];

  const VISIBLE_CONTENT_OPTIONS = [
    {
      label: 'Main User properties',
      options: [
        { id: 'id', label: 'User ID', editable: false },
        { id: 'name', label: 'User name' }
      ],
    },
  ];

  const Preferences = ({
    preferences,
    setPreferences,
    disabled,
    pageSizeOptions = PAGE_SIZE_OPTIONS,
    visibleContentOptions = VISIBLE_CONTENT_OPTIONS,
  }) => (
    <CollectionPreferences
      title="Preferences"
      confirmLabel="Confirm"
      cancelLabel="Cancel"
      disabled={disabled}
      preferences={preferences}
      onConfirm={({ detail }) => setPreferences(detail)}
      pageSizePreference={{
        title: 'Page size',
        options: pageSizeOptions,
      }}
      wrapLinesPreference={{
        label: 'Wrap lines',
        description: 'Check to see all the text and wrap the lines',
      }}
      visibleContentPreference={{
        title: 'Select visible columns',
        options: visibleContentOptions,
      }}
    />
  );

export function UserPropertyFilterTable({
  data,
  loadHelpPanelContent,
  columnDefinitions,
  saveWidths,
  preferences,
  setPreferences,
  filteringProperties,
}) {
  const { items, actions, filteredItemsCount, collectionProps, paginationProps, propertyFilterProps } = useCollection(
    data,
    {
      propertyFiltering: {
        filteringProperties,
        empty: <TableEmptyState resourceName="User" />,
        noMatch: (
          <TableNoMatchState
            onClearFilter={() => {
              actions.setPropertyFiltering({ tokens: [], operation: 'and' });
            }}
          />
        ),
      },
      pagination: { pageSize: preferences.pageSize },
      sorting: { defaultState: { sortingColumn: columnDefinitions[0] } },
      selection: {},
    }
  );

  return (
    <Table
      {...collectionProps}
      items={items}
      columnDefinitions={columnDefinitions}
      visibleColumns={preferences.visibleContent}
      ariaLabels={distributionSelectionLabels}
      selectionType="multi"
      variant="full-page"
      stickyHeader={true}
      resizableColumns={true}
      wrapLines={preferences.wrapLines}
      onColumnWidthsChange={saveWidths}
      header={
        <FullPageHeader
          selectedItems={collectionProps.selectedItems}
          totalItems={data}
          loadHelpPanelContent={loadHelpPanelContent}
          serverSide={false}
        />
      }
      loadingText="Loading Users"
      filter={
        <PropertyFilter
          i18nStrings={PROPERTY_FILTERING_I18N_CONSTANTS}
          {...propertyFilterProps}
          countText={getFilterCounterText(filteredItemsCount)}
          expandToViewport={true}
        />
      }
      pagination={<Pagination {...paginationProps} ariaLabels={paginationLabels} />}
      preferences={<Preferences preferences={preferences} setPreferences={setPreferences} />}
    />
  );
}