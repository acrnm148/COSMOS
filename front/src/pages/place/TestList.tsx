import React, { useState } from "react";
import TestItem from "./TestItem";
import { DragDropContext, Draggable, Droppable } from "react-beautiful-dnd";

export default function TestList({ items }: any) {
  const [item, setItems] = useState(items);
  const onDragEnd = (result: any) => {
    const { destination, source, draggableId } = result;
    if (!destination) {
      return;
    }
    if (
      destination.droppableId === source.droppableId &&
      destination.index === source.index
    ) {
      return;
    }

    const start = result.source.index;
    const end = result.destination.index;

    const totalItem = Array.from(item);
    const itemLength = totalItem.length;

    if (start < end) {
      const thisItem = totalItem.splice(start, 1);
      const middleItem = totalItem.splice(start, end - start);
      const startItem = totalItem.splice(0, start);
      const endItem = totalItem;
      const newItem = startItem.concat(middleItem, thisItem, endItem);

      setItems(newItem);
    } else {
      const thisItem = totalItem.splice(start, 1);
      const middleItem = totalItem.splice(end, start - end);
      const endItem = totalItem.splice(end, itemLength);
      const startItem = totalItem;
      console.log(startItem, thisItem, middleItem, endItem);
      const newItem = startItem.concat(thisItem, middleItem, endItem);

      setItems(newItem);
    }
  };

  return (
    <DragDropContext onDragEnd={onDragEnd}>
      <Droppable droppableId="cardlists">
        {(provided) => (
          <div
            id="cardLists"
            className="cardlists p-[10px] border-[2px]"
            {...provided.droppableProps}
            ref={provided.innerRef}
          >
            {item.map((e: any, i: number) => (
              <Draggable draggableId={e.sidoCode} index={i} key={e.sidoCode}>
                {(provided, snapshot) => {
                  return (
                    <div
                      className="p-[8px] m-[8px] border-[4px] rounded-lg bg-[#ffffff]"
                      {...provided.draggableProps}
                      {...provided.dragHandleProps}
                      ref={provided.innerRef}
                    >
                      <TestItem sidoName={e.sidoName} />
                    </div>
                  );
                }}
              </Draggable>
            ))}
          </div>
        )}
      </Droppable>
    </DragDropContext>
  );
}
